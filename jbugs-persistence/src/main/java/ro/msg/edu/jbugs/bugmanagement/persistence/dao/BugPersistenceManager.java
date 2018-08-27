package ro.msg.edu.jbugs.bugmanagement.persistence.dao;

import ro.msg.edu.jbugs.bugmanagement.persistence.entity.*;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;
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

    /**
     * @param title
     * @param description
     * @param status
     * @param severity
     * @return: List of Bugs, filtered by the given parameters.
     */
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

    /**
     * @param title
     * @param version
     * @return: List of bugs, sorted by given parameters.
     */
    public List<Bug> sort(boolean title, boolean version){

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Bug> cq = builder.createQuery(Bug.class);
        Metamodel metamodel = em.getMetamodel();

        EntityType<Bug> entityType = metamodel.entity(Bug.class);
        Root<Bug> root = cq.from(entityType);

        List<Order> result = new ArrayList<>();

        if(title){
            result.add(builder.asc(root.get("title")));
        }

        if(version){
            result.add(builder.asc(root.get("version")));
        }

        if(!result.isEmpty()){
            cq.orderBy(result);
        }

        return em.createQuery(cq).getResultList();
    }

    /**
     * Updates a bug from the database.
      * @param bug: bug entity to be updated, should not be null
     */
    public void updateBug(@NotNull Bug bug) {
        em.merge(bug);
    }

}
