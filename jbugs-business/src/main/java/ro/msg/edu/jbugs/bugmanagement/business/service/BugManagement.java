package ro.msg.edu.jbugs.bugmanagement.business.service;

import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;

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
    List<BugDTO> getBugsWithId(List<Long> titles);
    /**
     * Returns a bug entity with the matching id wrapped in an optional.
     * If none exist, returns an empty Optional Object
     *
     * @param id : Long containing the id.
     * @return : Optional, containing a bug entity.
     */
    BugDTO getBugById(Long id) throws BusinessException;


    BugDTO createBug(BugDTO bugDTO) throws BusinessException;

    /**
     * @return a list of DTOs containing information about bugs, sorted by different parameters
     */
    List<BugDTO> sort(boolean title, boolean version) throws BusinessException;

    boolean isBugValid(Bug bug) throws BusinessException;

    /**
     * @return a list of DTOs containing information about bugs, filtered by different parameters
     */
    List<BugDTO> filter(String title, String description, Status status, Severity severity) throws BusinessException;


    boolean validateDescription(String description) throws BusinessException;


    boolean validateVersion(String version) throws BusinessException;

    BugDTO updateBug(BugDTO bugDTO) throws BusinessException;

}


