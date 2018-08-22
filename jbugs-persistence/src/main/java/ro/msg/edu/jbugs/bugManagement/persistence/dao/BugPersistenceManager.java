package ro.msg.edu.jbugs.bugManagement.persistence.dao;

import ro.msg.edu.jbugs.bugManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Status;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;


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

    /**
     * Returns a user entity with the matching title wrapped in an optional.
     * If none exist, returns an empty Optional Object
     * @param title : String containing the title.
     * @return : Optional, containing a bug entity.
     */
    public Optional<Bug> getBugByTitle(@NotNull String title){
        TypedQuery<Bug> q = em.createNamedQuery(Bug.GET_BUG_BY_TITLE, Bug.class)
                .setParameter("title", title);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /**
     * Returns a user entity with the matching title wrapped in an optional.
     * If none exist, returns an empty Optional Object
     * @param title : String containing the title.
     * @return : List of Bugs, empty if there are no roles in the database.
     */
    public List<Bug> getBugsByTitle(@NotNull String title) {
        //Query q = em.createQuery("SELECT b FROM Bug b WHERE b.title="+ title);
        TypedQuery<Bug> q=em.createNamedQuery(Bug.GET_BUG_BY_TITLE, Bug.class)
                .setParameter("title",title);
        return q.getResultList();
    }

    /**
     * Returns a user entity with the matching status wrapped in an optional.
     * If none exist, returns an empty Optional Object
     * @param status : String containing the status.
     * @return : List of Bugs, empty if there are no roles in the database.
     */
    public List<Bug> getBugsByStatus(@NotNull Status status){
        TypedQuery<Bug> q=em.createNamedQuery(Bug.GET_BUG_BY_STATUS, Bug.class)
                .setParameter("status",status);
        return q.getResultList();

    }

    /**
     * Returns a user entity with the matching severity wrapped in an optional.
     * If none exist, returns an empty Optional Object
     * @param severity : String containing the severity.
     * @return : List of Bugs, empty if there are no roles in the database.
     */
    public List<Bug> getBugsBySeverity(@NotNull Severity severity){
        TypedQuery<Bug> q=em.createNamedQuery(Bug.GET_BUG_BY_SEVERITY, Bug.class)
                .setParameter("severity",severity);
        return q.getResultList();

    }


    public Optional<Bug> getBugById(@NotNull Long id){
        TypedQuery<Bug> q=em.createNamedQuery(Bug.GET_BUG_BY_ID,Bug.class)
                .setParameter("id",id);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

//    public Bug getBugById(@NotNull Long id){
//        TypedQuery<Bug> q=em.createNamedQuery(Bug.,Bug.class)
//                .setParameter("id",id);
//        return q.getSingleResult();
//    }

}
