package ro.msg.edu.jbugs.bugManagement.persistence.dao;

import ro.msg.edu.jbugs.bugManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;


@Stateless
public class BugPersistenceManager {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;


    /**
     * Get a list of all bugs stored in the database.
     * @return : List of Bugs, empty if there are no bugs in the database.
     */
    public List<Bug> getAllBugs() {
        TypedQuery<Bug> q = em.createNamedQuery(Bug.GET_ALL_BUGS,Bug.class);
        return q.getResultList();
    }

    public Bug getBugById(@NotNull Long id){
        TypedQuery<Bug> q=em.createNamedQuery(Bug.GET_BUG_BY_ID,Bug.class)
                .setParameter("id",id);
        return q.getSingleResult();
    }

}
