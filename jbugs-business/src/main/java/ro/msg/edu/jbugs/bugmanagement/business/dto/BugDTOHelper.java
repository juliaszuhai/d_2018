package ro.msg.edu.jbugs.bugmanagement.business.dto;


import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;


public class BugDTOHelper {



    private BugDTOHelper() {
        //hiding the default constructor.
    }

    public static BugDTO fromEntity(Bug bug) {

        BugDTO bugDTO = new BugDTO();
        bugDTO.setId(bug.getId());
        bugDTO.setTitle(bug.getTitle());
        bugDTO.setDescription(bug.getDescription());
        bugDTO.setVersion(bug.getVersion());
        bugDTO.setTargetDate(bug.getTargetDate());
        bugDTO.setStatus(bug.getStatus());
        bugDTO.setFixedVersion(bug.getFixedVersion());
        bugDTO.setSeverity(bug.getSeverity());

        return bugDTO;

    }

    public static Bug updateBugWithDTO(Bug bug, BugDTO bugDTO){
        bug.setId(bugDTO.getId());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setVersion(bugDTO.getVersion());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setStatus(Status.valueOf(bugDTO.getStatusString()));
        bug.setSeverity(Severity.valueOf(bugDTO.getSeverityString()));

        return bug;
    }


    public static Bug toEntity(BugDTO bugDTO) {
        Bug bug = new Bug();

        bug.setId(bugDTO.getId());
        bug.setTitle(bugDTO.getTitle());
        bug.setDescription(bugDTO.getDescription());
        bug.setTargetDate(bugDTO.getTargetDate());
        bug.setStatus(bugDTO.getStatus());
        bug.setFixedVersion(bugDTO.getFixedVersion());
        bug.setSeverity(bugDTO.getSeverity());

        return bug;
    }



}
