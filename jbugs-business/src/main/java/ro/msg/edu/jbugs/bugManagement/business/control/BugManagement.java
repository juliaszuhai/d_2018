package ro.msg.edu.jbugs.bugManagement.business.control;

import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.dto.*;

import java.util.List;

public interface BugManagement {

    /**
     * @return a list of DTOs containing information about bugs.
     */
    List<BugDTO> getAllBugs();

    /**
     * Return a list of bugs which have their ids contained in another list
     * @param titles : List<Long>
     * @return List<BugDTO>
     */
    public List<BugDTO> getBugsWithId(List<Long> titles);
    /**
     * Returns a bug entity with the matching id wrapped in an optional.
     * If none exist, returns an empty Optional Object
     *
     * @param id : Long containing the id.
     * @return : Optional, containing a bug entity.
     */
    BugDTO getBugById(Long id) throws BusinessException;


    /**
     * @return a list of DTOs containing information about bugs, filtered by title.
     */
    List<BugDTO> getBugsByTitle(String title) throws BusinessException;

    /**
     * @return a list of DTOs containing information about bugs, filtered by status.
     */
    List<BugDTO> getBugsByStatus(Status status) throws BusinessException;

    /**
     * @return a list of DTOs containing information about bugs, filtered by severity.
     */
    List<BugDTO> getBugsBySeverity(Severity severity) throws BusinessException;

    BugDTO createBug(BugDTO bugDTO);


}
