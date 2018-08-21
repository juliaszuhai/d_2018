package ro.msg.edu.jbugs.bugManagement.business.control;

import ro.msg.edu.jbugs.bugManagement.business.boundary.ListWrapper;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.bugManagement.persistence.dao.BugPersistenceManager;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
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

    @Override
    public List<BugDTO> getBugsWithTitle(ListWrapper titles) {
        List<BugDTO> bugs=this.getAllBugs();
        List<BugDTO> selectedBugs=new ArrayList<BugDTO>();
        for(int k=0;k<titles.getTitles().size();k++)
        {
            for(int l=0;l<bugs.size();l++)
            {
                if(titles.getTitles().get(k).equals(bugs.get(l).getTitle()))
                {
                    selectedBugs.add(bugs.get(l));
                }
            }
        }
        return selectedBugs;
    }
}
