package ro.msg.edu.jbugs.bugmanagement.business.service;

import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.bugmanagement.business.dto.NameIdDTO;
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
        /*List<BugDTO> bugs=new ArrayList<BugDTO>();
        for(Bug b:bugPersistenceManager.getAllBugs())
        {
            BugDTO bug=BugDTOHelper.fromEntity(b);
            this.setUsers(bug,b);
            bugs.add(bug);
        }
        return bugs;*/
        return bugPersistenceManager.getAllBugs()
                .stream()
                .map(bug ->{
                    BugDTO bugDTO=BugDTOHelper.fromEntity(bug);
                    this.setUsers(bugDTO,bug);
                    return bugDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<BugDTO> getBugsWithId(List<Long> titles) {
        List<BugDTO> bugs =this.getAllBugs();
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

//
//    public List<BugDTO> filterAndSort(List<String> filterArgs, List<Boolean> sortArgs){
//        Status status = Status.valueOf(filterArgs.get(2));
//        Severity severity = Severity.valueOf(filterArgs.get(3));
//
//        List<BugDTO> bugDTOs = filter(filterArgs.get(0),filterArgs.get(1),status,severity);
//        return sort(sortArgs.get(0),sortArgs.get(1));
//
//    }

    @Override
    public List<BugDTO> filter(String title, String description, Status status, Severity severity) {
        List<Bug> filteredBugs= bugPersistenceManager.filter(title, description, status, severity)
                                .stream()
                                .collect(Collectors.toList());
        return filteredBugs
                .stream()
                .map(bug ->{
                        BugDTO bugDTO=BugDTOHelper.fromEntity(bug);
                        this.setUsers(bugDTO,bug);
                        return bugDTO;
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<BugDTO> sort(boolean title, boolean version) {
        List<Bug> sorteddBugs= bugPersistenceManager.sort(title, version)
                .stream()
                .collect(Collectors.toList());
        return sorteddBugs
                .stream()
                .map(bug ->{
                    BugDTO bugDTO=BugDTOHelper.fromEntity(bug);
                    this.setUsers(bugDTO,bug);
                    return bugDTO;
                })
                .collect(Collectors.toList());
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
        if(userAssigned.isPresent()) {
            bug.setAssignedTo(userAssigned.get());
        } else {
            throw new BusinessException(ExceptionCode.COULD_NOT_CREATE_BUG);
        }

        Optional<User> userCreated = userPersistenceManager.getUserByUsername(bugDTO.getCreatedByUserString());
        if(userCreated.isPresent()){
            bug.setCreatedByUser(userCreated.get());
        } else {
            throw new BusinessException(ExceptionCode.COULD_NOT_CREATE_BUG);
        }
            this.isBugValid(bug);
            bugPersistenceManager.createBug(bug);
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
    public BugDTO updateBug(BugDTO bugDTO) {
        return null;
    }

    @Override
    public BugDTO setUsers(BugDTO bugDTO,Bug bug) {
        NameIdDTO createdBy = new NameIdDTO();

        createdBy.setId(bug.getCreatedByUser().getId());
        createdBy.setUsername(bug.getCreatedByUser().getUsername());
        bugDTO.setCreatedByUser(createdBy);

        NameIdDTO assignedTo = new NameIdDTO();

        assignedTo.setId(bug.getAssignedTo().getId());
        assignedTo.setUsername(bug.getAssignedTo().getUsername());
        bugDTO.setAssignedTo(assignedTo);
        return bugDTO;
    }

    @Override
    public BugDTO getBugById(Long id) throws BusinessException {
        Optional<Bug> bug = bugPersistenceManager.getBugById(id);
        return BugDTOHelper.fromEntity(bug.orElseThrow(()->new BusinessException(ExceptionCode.BUG_NOT_EXIST)));



    }

}
