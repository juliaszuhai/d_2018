package ro.msg.edu.jbugs.bugmanagement.persistence.dao;

import javafx.util.Pair;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Attachment;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
     *
     * @return : List of Bugs, empty if there are no bugs in the database.
     */
    public List<Bug> getAllBugs() {
        TypedQuery<Bug> q = em.createNamedQuery(Bug.GET_ALL_BUGS, Bug.class);
        return q.getResultList();
    }

    public List<Attachment> getAllAttachments() {
        TypedQuery<Attachment> q = em.createNamedQuery(Attachment.GET_ALL_ATTACHMENTS, Attachment.class);
        return q.getResultList();
    }


    public List<Attachment> getAttachmentsForBug(@NotNull Long id) {
        TypedQuery<Attachment> q = em.createNamedQuery(Attachment.GET_ATTACHMENTS_FOR_BUG, Attachment.class).setParameter("bugId", id);
        return q.getResultList();
    }


    /**
     * @param id
     * @return: Optional, containing a bug entity.
     */
    public Optional<Bug> getBugById(@NotNull Long id) {
        TypedQuery<Bug> q = em.createNamedQuery(Bug.GET_BUG_BY_ID, Bug.class)
                .setParameter("id", id);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    /**
     * Persists a bug in the database.
     *
     * @param bug : bug entity to be created, should not be null
     */
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
    public Pair<Long, List<Bug>> filter(String title, String description, Status status, Severity severity, Long id, int index, int amount) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Bug> criteriaQuery = builder.createQuery(Bug.class);
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Metamodel metamodel = em.getMetamodel();

        EntityType<Bug> entityType = metamodel.entity(Bug.class);
        Root<Bug> root = criteriaQuery.from(entityType);

        buildFilterCriteria(title, description, status, severity, id, builder, criteriaQuery, root);
        buildFilterCriteria(title, description, status, severity, id, builder, countCriteria, root);

        TypedQuery<Bug> query = em.createQuery(criteriaQuery);
        countCriteria.select(builder.count(root));
        TypedQuery<Long> countQuery = em.createQuery(countCriteria);
        Long amountOfResults = new Long(query.getResultList().size());
        query.setFirstResult(index);
        query.setMaxResults(amount);


        return new Pair<>(amountOfResults, query.getResultList());
    }

    private void buildFilterCriteria(String title, String description, Status status, Severity severity, Long id, CriteriaBuilder builder, CriteriaQuery criteriaQuery, Root<Bug> root) {
        List<Predicate> result = new ArrayList<>();

        if (description != null) {
            result.add(builder.like(root.get("description"), "%" + description + "%"));
        }

        if (title != null) {
            result.add(builder.like(root.get("title"), "%" + title + "%"));
        }

        if (status != null) {
            result.add(builder.equal(root.get("status"), status));

        }

        if (severity != null) {
            result.add(builder.equal(root.get("severity"), severity));

        }

        if (id != null) {
            result.add(builder.equal(root.get("id"), id));
        }
        if (!result.isEmpty()) {
            criteriaQuery.where(result.toArray(new Predicate[0]));
        }
    }

    /**
     * Updates a bug from the database.
     *
     * @param bug: bug entity to be updated, should not be null
     */
    public void updateBug(@NotNull Bug bug) {
        em.merge(bug);
    }

    /**
     * Count the bags that have a certain status
     *
     * @param status
     * @return: number of bugs that have the specified status
     */
    public Optional<Long> countBugsByStatus(@NotNull Status status) {
        TypedQuery<Long> q = em.createNamedQuery(Bug.COUNT_BUG_BY_STATUS, Long.class);
        q.setParameter("status", status);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public void addAttachment(Attachment attachment) {
        em.persist(attachment);
        em.flush();
    }


    public Optional<Attachment> getAttachmentForName(String name) {
        TypedQuery<Attachment> q = em.createNamedQuery(Attachment.GET_ATTACHMENT_FOR_NAME, Attachment.class);
        q.setParameter("name", name);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public void createBugWithAttachment(Bug bug, Attachment attachment) {
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(attachment);
        bug.setAttachments(attachments);
        em.persist(bug);
        em.flush();

    }
}
