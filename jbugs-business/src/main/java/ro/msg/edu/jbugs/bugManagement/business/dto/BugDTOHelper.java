package ro.msg.edu.jbugs.bugManagement.business.dto;


import ro.msg.edu.jbugs.bugManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

public class BugDTOHelper {

    @EJB
    private static UserPersistenceManager userPersistenceManager;

    public static BugDTO fromEntity(Bug bug){

        BugDTO bugDTO=new BugDTO();

        bug.setId(bug.getId());
        bugDTO.setTitle(bug.getTitle());
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setVersion(bug.getVersion());
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setStatus(bug.getStatus());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setSeverity(bug.getSeverity());

        NameIdDTO createdBy=new NameIdDTO();

        createdBy.setId(bug.getCreatedByUser().getId());
        createdBy.setUsername(bug.getCreatedByUser().getUsername());
        bugDTO.setCreatedByUser(createdBy);

        NameIdDTO assignedTo=new NameIdDTO();

        assignedTo.setId(bug.getAssignedTo().getId());
        assignedTo.setUsername(bug.getAssignedTo().getUsername());
        bugDTO.setAssignedTo(assignedTo);

        return bugDTO;

    }

    public static Bug toEntity(BugDTO bugDTO){
        Bug bug = new Bug();

        bug.setId(bugDTO.getId());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setStatus(bugDTO.getStatus());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity());

        User createdByUser;

        Long createByUserId=bugDTO.getCreatedByUser().getId();
        System.out.println("BugDTOHelper"+ createByUserId);
        createdByUser=userPersistenceManager.getUserById(createByUserId);
        bug.setCreatedByUser(createdByUser);


        User assignedTo=new User();

        Long assignedToId=bugDTO.getAssignedTo().getId();
        System.out.println("BugDTOHelper"+ assignedToId);
        assignedTo=userPersistenceManager.getUserById(assignedToId);
        bug.setAssignedTo(assignedTo);

        return bug;
    }

}
