package ro.msg.edu.jbugs.bugmanagement.business.service;

import javafx.util.Pair;
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
import ro.msg.edu.jbugs.usermanagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class BugManagementService implements BugManagement {

    @EJB
    private BugPersistenceManager bugPersistenceManager;

    @EJB
    private UserPersistenceManager userPersistenceManager;

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


    public BugDTO createBug(BugDTO bugDTO) throws BusinessException {
        Bug bug = new Bug();
        Date date = null;
        try {
            date = new java.sql.Date(new SimpleDateFormat("yyyy-mm-dd").parse(bugDTO.getTargetDateString()).getTime());
        } catch (ParseException e) {
            throw new BusinessException(ExceptionCode.COULD_NOT_CREATE_BUG);
        }
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setVersion(bugDTO.getVersion());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setTargetDate(date);
        bug.setSeverity(bugDTO.getSeverity());
        bug.setStatus(Status.NEW);

        Optional<User> userAssigned = userPersistenceManager.getUserByUsername(bugDTO.getAssignedToString());
        if (userAssigned.isPresent()) {
            bug.setAssignedTo(userAssigned.get());
        } else {
            throw new BusinessException(ExceptionCode.COULD_NOT_CREATE_BUG);
        }

        Optional<User> userCreated = userPersistenceManager.getUserByUsername(bugDTO.getCreatedByUserString());
        if (userCreated.isPresent()) {
            bug.setCreatedByUser(userCreated.get());
        } else {
            throw new BusinessException(ExceptionCode.COULD_NOT_CREATE_BUG);
        }

        this.isBugValid(bug);
        bugPersistenceManager.createBug(bug);
        if (bugDTO.getAttachments().length > 0) {
            for (int i = 0; i < bugDTO.getAttachments().length; i++) {
                try {
                    Attachment attachment = new Attachment();
                    Blob blob = new javax.sql.rowset.serial.SerialBlob(bugDTO.getAttachments()[i].getAttachmentFile());
                    attachment.setAttachmentFile(blob);
                    attachment.setName(bugDTO.getAttachments()[i].getName());
                    attachment.setExtension(bugDTO.getAttachments()[i].getExtension());
                    bugPersistenceManager.addAttachmentToBug(bug, attachment);
                } catch (SQLException e) {
                    throw new BusinessException(ExceptionCode.COULD_NOT_CREATE_BUG);
                }
            }
        }
        return bugDTO;

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


    @Override
    public BugDTO updateBug(BugDTO bugDTO) throws BusinessException {

        Optional<Bug> bugBeforeOpt = bugPersistenceManager.getBugById(bugDTO.getId());
        if (bugBeforeOpt.isPresent()) {
            Bug bugBefore = bugBeforeOpt.get();


            Bug bugAfter = BugDTOHelper.updateBugWithDTO(bugBefore, bugDTO);

            bugAfter = setUsersFromDTO(bugDTO, bugAfter);

            List<Integer> succesors = bugBefore.getStatus().getSuccesors();
            System.out.println("vai" + succesors);


            Integer val = Status.valueOf(bugDTO.getStatusString()).getValue();
            System.out.println("caca" + val);


            if (succesors.contains(val)) {
                bugAfter.setStatus(Status.valueOf(bugDTO.getStatusString()));
                this.isBugValid(bugAfter);
                bugPersistenceManager.updateBug(bugAfter);

            } else {
                throw new BusinessException(ExceptionCode.STATUS_INCORRECT_EXCEPTION);
            }

//            this.isBugValid(bugAfter);
//            bugPersistenceManager.updateBug(bugAfter);
            return bugDTO;
        } else {
            throw new BusinessException(ExceptionCode.BUG_VALIDATION_EXCEPTION);
        }
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
        if (createdByOp.isPresent()) {
            User createdByUser = createdByOp.get();
            bug.setCreatedByUser(createdByUser);
        } else {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXIST);
        }

        Optional<User> assignedToUserOp = userPersistenceManager.getUserByUsername(bugDTO.getAssignedToString());
        if (assignedToUserOp.isPresent()) {
            User assignedTo = assignedToUserOp.get();
            bug.setAssignedTo(assignedTo);
        } else {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXIST);
        }


        return bug;
    }


    public Bug setUsers(BugDTO bugDTO, Bug bug) throws ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException {

        Long createByUserId = bugDTO.getCreatedByUser().getId();
        Optional<User> createdByOp = userPersistenceManager.getUserById(createByUserId);
        if (createdByOp.isPresent()) {
            User createdByUser = createdByOp.get();
            bug.setCreatedByUser(createdByUser);
        } else {
            throw new ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException(ro.msg.edu.jbugs.usermanagement.business.exceptions.ExceptionCode.USER_NOT_EXIST);
        }

        Long assignedToId = bugDTO.getAssignedTo().getId();
        Optional<User> assignedToUserOp = userPersistenceManager.getUserById(assignedToId);
        if (assignedToUserOp.isPresent()) {
            User assignedTo = assignedToUserOp.get();
            bug.setAssignedTo(assignedTo);
        } else {
            throw new ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException(ro.msg.edu.jbugs.usermanagement.business.exceptions.ExceptionCode.UNKNOWN_EXCEPTION);
        }
        return bug;
    }


    @Override
    public BugDTO createBugWithAttachment(BugDTO bugDTO, byte[] bytes) throws BusinessException, ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException {
        try {
            Attachment attachment = new Attachment();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            attachment.setAttachmentFile(blob);
            BugDTO bugDTOCreated = this.createBug(bugDTO);
            Bug bugCreated = BugDTOHelper.toEntity(bugDTOCreated);
            this.setUsers(bugDTOCreated, bugCreated);
            bugPersistenceManager.addAttachmentToBug(bugCreated, attachment);
            return bugDTO;

        } catch (BusinessException | SQLException e) {
            throw new BusinessException(ExceptionCode.COULD_NOT_CREATE_BUG);
        }

    }

    @Override
    public BugDTO addAttachmentToBug(BugDTO bugDTO, Attachment attachment) throws ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException {
        try {
            Bug bug = BugDTOHelper.toEntity(bugDTO);
            this.setUsers(bugDTO, bug);
            bugPersistenceManager.addAttachmentToBug(bug, attachment);
            return bugDTO;
        } catch (ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException e) {
            throw new ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException();
        }
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
}
