package ro.msg.edu.jbugs.bugmanagement.business.control;

import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.bugmanagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class BugManagementController  implements BugManagement {

    @EJB
    private BugPersistenceManager bugPersistenceManager;

    @EJB
    private UserPersistenceManager userPersistenceManager;

    @Override
    public List<BugDTO> getAllBugs() {
        return bugPersistenceManager.getAllBugs()
                .stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<BugDTO> getBugsWithId(List<Long> titles) {
        List<BugDTO> bugs = bugPersistenceManager.getAllBugs().stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());
        List<BugDTO> selectedBugs = new ArrayList<BugDTO>();
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
    public List<BugDTO> filter(String title, String description, Status status, Severity severity) throws BusinessException {
        return bugPersistenceManager.filter(title, description, status, severity)
                .stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<BugDTO> sort(boolean title, boolean version) throws BusinessException {
        return bugPersistenceManager.sort(title, version)
                .stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }


    public BugDTO createBug(BugDTO bugDTO) throws BusinessException {
        Bug bug = new Bug();
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setVersion(bugDTO.getVersion());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setSeverity(bugDTO.getSeverity());
        bug.setStatus(bugDTO.getStatus());


        Optional<User> userAssigned = userPersistenceManager.getUserById(bugDTO.getAssignedTo().getId());
        if(userAssigned.isPresent()) {
            bug.setAssignedTo(userAssigned.get());
        } else {
            throw new BusinessException(); // TODO : adauga exceptie buna
        }

        Optional<User> userCreated = userPersistenceManager.getUserById(bugDTO.getCreatedByUser().getId());
        if(userCreated.isPresent()){
            bug.setCreatedByUser(userCreated.get());
        } else {
            throw new BusinessException(); // TODO : adauga exceptie buna
        }
        try{
            this.isBugValid(bug);
            bugPersistenceManager.createBug(bug);
            return bugDTO;
        } catch (BusinessException e) {
            throw e;
        }
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
        final Pattern VALID_VERSION_REGEX =
                Pattern.compile("([a-zA-Z0-9]+).([a-zA-Z0-9]+).([a-zA-Z0-9]+)", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_VERSION_REGEX.matcher(version);
        if (matcher.find() == false)
            throw new BusinessException(ExceptionCode.VERSION_NOT_VALID);

        return true;
    }

    @Override
    public BugDTO getBugById(Long id) throws BusinessException {
        Optional<Bug> bug = bugPersistenceManager.getBugById(id);
        if (bug.isPresent()) {
            return BugDTOHelper.fromEntity(bug.get());
        } else {
            throw new BusinessException(ExceptionCode.BUG_NOT_EXIST);
        }

    }

}
