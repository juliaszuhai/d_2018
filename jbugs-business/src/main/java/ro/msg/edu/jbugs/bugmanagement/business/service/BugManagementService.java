package ro.msg.edu.jbugs.bugmanagement.business.service;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.bugmanagement.business.dto.FilterDTO;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.bugmanagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Attachment;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;
import ro.msg.edu.jbugs.notificationmanagement.business.service.NotificationManagementService;
import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.TypeNotification;
import ro.msg.edu.jbugs.usermanagement.business.control.AuthenticationManager;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class BugManagementService implements BugManagement {

    static Logger log = LogManager.getLogger(AuthenticationManager.class.getName());
    @EJB
    private BugPersistenceManager bugPersistenceManager;

    @EJB
    private UserPersistenceManager userPersistenceManager;

    @EJB
    private NotificationManagementService notificationManagementService;

    @Override
    public List<BugDTO> getAllBugs() {
        return bugPersistenceManager.getAllBugs()
                .stream()
                .map(bug -> {
                    BugDTO bugDTO = BugDTOHelper.fromEntity(bug);
                    return bugDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<BugDTO> getBugsWithId(List<Long> titles) {
        List<BugDTO> bugs = this.getAllBugs();
        List<BugDTO> selectedBugs = new ArrayList<>();
        for (int k = 0; k < titles.size(); k++) {
            for (int l = 0; l < bugs.size(); l++) {
                if (titles.get(k).equals(bugs.get(l).getId())) {
                    selectedBugs.add(bugs.get(l));
                }
            }
        }
        return selectedBugs;
    }


    @Override
    public FilterDTO filter(String title, String description, Status status, Severity severity, int index, int amount, Long id) {
        Pair<Long, List<Bug>> filterPair = bugPersistenceManager.filter(title, description, status, severity, id, index, amount);
        FilterDTO filtered = new FilterDTO();

        filtered.setFilteredList(BugDTOHelper.fromBugList(filterPair.getValue()));
        filtered.setActualListSize(filterPair.getKey());
        return filtered;
    }


    public Bug createBug(BugDTO bugDTO) throws BusinessException {
        Bug bug = new Bug();
        List<User> receivers = new ArrayList<>();

        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setVersion(bugDTO.getVersion());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setSeverity(bugDTO.getSeverity());
        bug.setStatus(Status.NEW);

        Optional<User> userAssigned = userPersistenceManager.getUserByUsername(bugDTO.getAssignedTo().getUsername());
        if (userAssigned.isPresent()) {
            bug.setAssignedTo(userAssigned.get());
            receivers.add(userAssigned.get());
        } else {
            throw new BusinessException(ExceptionCode.COULD_NOT_CREATE_BUG);
        }

        Optional<User> userCreated = userPersistenceManager.getUserByUsername(bugDTO.getCreatedByUser().getUsername());
        if (userCreated.isPresent()) {
            bug.setCreatedByUser(userCreated.get());
            receivers.add(userCreated.get());
        } else {
            throw new BusinessException(ExceptionCode.COULD_NOT_CREATE_BUG);
        }

        this.isBugValid(bug);
        bugPersistenceManager.createBug(bug);
        notificationManagementService.sendNotification(TypeNotification.BUG_CREATED, BugDTOHelper.fromEntity(bug), null, receivers);

        return bug;

    }


    @Override
    public boolean isBugValid(Bug bug) throws BusinessException {
        try {
            this.validateDescription(bug.getDescription());
            this.validateVersion(bug.getVersion());
            this.validateVersion(bug.getFixedVersion());
            return true;
        } catch (BusinessException e) {
            throw new BusinessException(e.getExceptionCode());
        }
    }


    @Override
    public boolean validateDescription(String description) throws BusinessException {
        if (description.length() < 250)
            throw new BusinessException(ExceptionCode.DESCRIPTION_TOO_SHORT);
        return true;
    }

    @Override
    public boolean validateVersion(String version) throws BusinessException {
        final Pattern validVersionRegex =
                Pattern.compile("([a-zA-Z0-9]+).([a-zA-Z0-9]+).([a-zA-Z0-9]+)", Pattern.CASE_INSENSITIVE);

        Matcher matcher = validVersionRegex.matcher(version);
        if (!matcher.find())
            throw new BusinessException(ExceptionCode.VERSION_NOT_VALID);

        return true;
    }


    /**
     * @param bugDTO
     * @param bug
     * @throws BusinessException
     * @return: a Bug with attributes (createdByUser, assignedToUser) set properly.
     */
    @Override
    public Bug setUsersFromDTO(BugDTO bugDTO, Bug bug) throws BusinessException {

        Optional<User> createdByOp = userPersistenceManager.getUserByUsername(bugDTO.getCreatedByUserString());
        User createdByUser = createdByOp.orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_EXIST));
        bug.setCreatedByUser(createdByUser);

        Optional<User> assignedToUserOp = userPersistenceManager.getUserByUsername(bugDTO.getAssignedToString());
        User assignedTo = assignedToUserOp.orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_EXIST));

        bug.setAssignedTo(assignedTo);


        return bug;
    }


    @Override
    public BugDTO getBugById(Long id) throws BusinessException {
        Optional<Bug> bugOptional = bugPersistenceManager.getBugById(id);
        Bug bug;
        if (bugOptional.isPresent()) {
            bug = bugOptional.get();
        } else {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXIST);
        }
        return BugDTOHelper.fromEntity(bug);


    }

    @Override
    public Long countBugsByStatus(Status status) throws BusinessException {
        Optional<Long> optionalNumberOfBug = bugPersistenceManager.countBugsByStatus(status);
        if (optionalNumberOfBug.isPresent()) {
            return optionalNumberOfBug.get();
        } else {
            throw new BusinessException(ExceptionCode.STATUS_INVALID);
        }
    }

    @Override
    public void addFileToBug(File file, String fileName, Long bugId) throws BusinessException {
        Optional<Bug> optBug = this.bugPersistenceManager.getBugById(bugId);
        if (optBug.isPresent()) {
            try {
                Bug bug = optBug.get();
                byte[] byteFile = Files.readAllBytes(file.toPath());
                Attachment attachment = new Attachment();
                attachment.setAttachmentFile(byteFile);
                attachment.setName(fileName);
                this.bugPersistenceManager.addAttachment(attachment);
                this.bugPersistenceManager.createBugWithAttachment(bug, attachment);
            } catch (IOException e) {
                throw new BusinessException(ExceptionCode.SOMETHING_WRONG_WITH_ATTACHMENT);
            }

        }
    }

    @Override
    public List<File> getAttachmentForBug(Long bugId) throws BusinessException {
        // attachments=bugPersistenceManager.getAttachmentsForBug(bugId);
        Optional<Bug> bug = bugPersistenceManager.getBugById(bugId);
        bug.orElseThrow(() -> new BusinessException(ExceptionCode.BUG_NOT_EXIST));
        List<Attachment> attachments = bug.get().getAttachments();
        List<File> files = new ArrayList<File>();
        for (int i = 0; i < attachments.size(); i++) {
            try {
                File f = new File(attachments.get(i).getName());
                byte[] blob = attachments.get(i).getAttachmentFile();
                ByteArrayInputStream bis = new ByteArrayInputStream(blob);
                OutputStream out = new FileOutputStream(f);
                out.write(blob);
                out.close();
                files.add(f);
            } catch (FileNotFoundException e) {
                throw new BusinessException(ExceptionCode.COULD_NOT_EXPORT_ATTACHMENT);
            } catch (IOException e) {
                throw new BusinessException(ExceptionCode.COULD_NOT_EXPORT_ATTACHMENT);
            }
        }
        if (files.size() < 1) {
            throw new BusinessException(ExceptionCode.ATTACHMENT_DOES_NOT_EXIST);
        }

        return files;
    }


    @Override
    public BugDTO updateBug(BugDTO bugDTO, String requester) throws BusinessException {

        Optional<Bug> bugBeforeOpt = bugPersistenceManager.getBugById(bugDTO.getId());
        Bug bugBefore = bugBeforeOpt.orElseThrow(() -> new BusinessException(ExceptionCode.BUG_NOT_EXIST));
        Integer statusBefore = bugBefore.getStatus().getValue();
        List<Integer> successors = bugBefore.getStatus().getSuccesors();
        Integer val = Status.valueOf(bugDTO.getStatusString()).getValue();

        bugBefore = BugDTOHelper.updateBugWithDTO(bugBefore, bugDTO);
        Bug bugAfter = setUsersFromDTO(bugDTO, bugBefore);

        successors.add(statusBefore);
        if (successors.contains(val)) {
            setBugStatus(bugDTO, bugBefore, bugAfter, TypeNotification.BUG_UPDATED);
        } else {
            throw new BusinessException(ExceptionCode.STATUS_INCORRECT_EXCEPTION);
        }
        return bugDTO;
    }

    @Override
    public void closeBug(Long bugId) throws BusinessException {
        Optional<Bug> bugOptional = bugPersistenceManager.getBugById(bugId);
        Bug bug = bugOptional.orElseThrow(() -> new BusinessException(ExceptionCode.BUG_NOT_EXIST));
		List<User> receivers = new ArrayList<>();
        if (bug.getStatus() != Status.REJECTED && bug.getStatus() != Status.FIXED) {
            throw new BusinessException(ExceptionCode.STATUS_INCORRECT_EXCEPTION);
        }
        bug.setStatus(Status.CLOSED);
		receivers.add(bug.getCreatedByUser());
		receivers.add(bug.getAssignedTo());
		notificationManagementService.sendNotification(TypeNotification.BUG_CLOSED, BugDTOHelper.fromEntity(bug), null, receivers);
    }

    private void setBugStatus(BugDTO bugDTO, Bug bugBefore, Bug bugAfter, TypeNotification typeNotification) throws BusinessException {
        List<User> receivers = new ArrayList<>();
        bugAfter.setStatus(Status.valueOf(bugDTO.getStatusString()));
        this.isBugValid(bugAfter);
        receivers.add(bugAfter.getAssignedTo());
        receivers.add(bugAfter.getCreatedByUser());
        bugPersistenceManager.updateBug(bugAfter);
        notificationManagementService.sendNotification(typeNotification.BUG_STATUS_UPDATED, BugDTOHelper.fromEntity(bugAfter), BugDTOHelper.fromEntity(bugBefore), receivers);
    }


    @Override
    public List<Status> getStatusSuccessor(Long id) throws BusinessException {
        Optional<Bug> bugOptional = bugPersistenceManager.getBugById(id);
        bugOptional.orElseThrow(() -> new BusinessException(ExceptionCode.BUG_NOT_EXIST));
        return convertStatus(bugOptional.get().getStatus().getSuccesors());
    }

    private List<Status> convertStatus(List<Integer> values) {
        List<Status> statuses = new ArrayList<>();
        values.forEach(value -> {
            if (value == 1) {
                statuses.add(Status.NEW);
            } else if (value == 2) {
                statuses.add(Status.IN_PROGRESS);
            } else if (value == 3) {
                statuses.add(Status.FIXED);
            } else if (value == 4) {
                statuses.add(Status.CLOSED);
            } else if (value == 5) {
                statuses.add(Status.REJECTED);
            } else if (value == 6) {
                statuses.add(Status.INFO_NEEDED);
            }
        });
        return statuses;
    }
}

