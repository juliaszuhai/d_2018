package ro.msg.edu.jbugs.bugManagement.business.control;

import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTOHelper;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.bugManagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Status;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public BugDTO getBugById(Long id) throws BusinessException {
        Optional<Bug> bug=bugPersistenceManager.getBugById(id);
        if(bug.isPresent()){
            return BugDTOHelper.fromEntity(bug.get());
        }else{
            throw new BusinessException(ExceptionCode.BUG_NOT_EXIST);
        }

    }

    @Override
    public List<BugDTO> getBugsWithTitle(List<String> titles) {
        List<BugDTO> bugs=bugPersistenceManager.getAllBugs().stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());
        List<BugDTO> selectedBugs=new ArrayList<BugDTO>();
        for(int k=0;k<titles.size();k++)
        {
            for(int l=0;l<bugs.size();l++)
            {
                if(titles.get(k).equals(bugs.get(l).getTitle()))
                {
                    selectedBugs.add(bugs.get(l));
                }
            }
        }
        return selectedBugs;
    }
    @Override
    public BugDTO getBugByTitle(String title) throws BusinessException {
        Optional<Bug> bug=bugPersistenceManager.getBugByTitle(title);
        if(bug.isPresent()){
            return BugDTOHelper.fromEntity(bug.get());
        }
        else{
            throw new BusinessException(ExceptionCode.BUG_NOT_EXPORTED);

        }

    }

    @Override
    public List<BugDTO> getBugsByTitle(String title) throws BusinessException {
        return bugPersistenceManager.getBugsByTitle(title)
                .stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<BugDTO> getBugsByStatus(Status status) throws BusinessException {
        return bugPersistenceManager.getBugsByStatus(status)
                .stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    public List<BugDTO> getBugsBySeverity(Severity severity) throws BusinessException {
        return bugPersistenceManager.getBugsBySeverity(severity)
                .stream()
                .map(BugDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }


}
