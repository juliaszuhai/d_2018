package ro.msg.edu.jbugs.bugManagement.persistence.dao;

import ro.msg.edu.jbugs.bugManagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Status;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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



    public Optional<Bug> getBugById(@NotNull Long id){
        TypedQuery<Bug> q=em.createNamedQuery(Bug.GET_BUG_BY_ID,Bug.class)
                .setParameter("id",id);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


    public Bug createBug(@NotNull Bug bug) {
        em.persist(bug);
        em.flush();
        return bug;
    }

    public List<Bug> filter(String title, String description, Status status, Severity severity) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Bug> cq = builder.createQuery(Bug.class);
        Metamodel metamodel = em.getMetamodel();

        EntityType<Bug> entityType = metamodel.entity(Bug.class);
        Root<Bug> root = cq.from(entityType);

        List<Predicate> result = new ArrayList<>();

        if (description != null) {
            result.add(builder.like(root.get("description"), "%" + description + "%"));
        }

        if (title != null) {
            result.add(builder.equal(root.get("title"), title));

        }

        if (status != null) {
            result.add(builder.equal(root.get("status"), status));

        }

        if (severity != null) {
            result.add(builder.equal(root.get("severity"), severity));

        }
        if (!result.isEmpty()) {
            cq.where(result.toArray(new Predicate[0]));
        }
        return em.createQuery(cq).getResultList();
    }

}
