package ro.msg.edu.jbugs.bugManagement.business.control;

import ro.msg.edu.jbugs.bugManagement.business.boundary.ListWrapper;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.*;

import java.util.List;

public interface BugManagement {

    /**
     * @return a list of DTOs containing information about bugs.
     */
    List<BugDTO> getAllBugs();

    List<BugDTO> getBugsWithTitle(ListWrapper titles);
}
