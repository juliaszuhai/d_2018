package ro.msg.edu.jbugs.bugManagement.business.control;

import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.bugManagement.persistence.dao.BugPersistenceManager;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class BugManagementController implements BugManagement {

    @EJB
    private BugPersistenceManager bugPersistenceManager;

    @Override
    public List<BugDTO> getAllBugs() {
        return bugPersistenceManager.getAllBugs()
                .stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());    }
}
