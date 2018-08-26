package ro.msg.edu.jbugs.usermanagement.persistence.dao;

import ro.msg.edu.jbugs.usermanagement.persistence.entity.Role;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Provides functions for working with users in the persistence layer.
 */
@Stateless
public class UserPersistenceManager {


    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;


    /**
     * Persists a user in the database.
     *
     * @param user : user entity to be created, should not be null
     */
    public User createUser(@NotNull User user) {
        em.persist(user);
        em.flush();
        return user;
    }

    /**
     * Updates a user from the database.
     *
     * @param user : user entity to be updated, should not be null
     */
    public void updateUser(@NotNull User user) {
        em.merge(user);
    }

    /**
     * Get a list of all users from the database.
     *
     * @return : ResultList, empty if there are no users in the database.
     */
    public List<User> getAllUsers() {
        return em.createNamedQuery(User.GET_ALL_USERS, User.class)
                .getResultList();
    }


    /**
     * Returns a user entity with the matching username wrapped in an optional.
     * If none exist, returns an empty Optional Object
     *
     * @param username : String containing the username.
     * @return : Optional, containing a user entity.
     */
    public Optional<User> getUserByUsername(@NotNull String username) {
        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_USERNAME, User.class)
                .setParameter("username", username);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }

    /**
     * Returns a user optional containing a user entity
     * with the corresponding Id from the database.
     *
     * @param id - id corresponding with the primary key from the user table in the db
     * @return Optional of user
     */
    public Optional<User> getUserById(@NotNull Long id) {
        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_ID, User.class)
                .setParameter("id", id);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


    /**
     * Persists a user in the database.
     *
     * @param role : role entity to be created, should not be null
     */
    public void createRole(@NotNull Role role) {
        em.persist(role);
    }

    /**
     * Removes a role from the database.
     *
     * @param role : role entity to be removed, should not be null
     */
    public void removeRole(Role role) {
        em.remove(role);
    }

    /**
     * Updates a role in the database using the given Role entity.
     *
     * @param role : role entity to be updated, should not be null
     * @return : returns the updated role entity
     */
    public Role updateRole(Role role) {
        em.merge(role);
        return role;
    }


    /**
     * Get a list of all roles stored in the database.
     *
     * @return : List of Roles, empty if there are no roles in the database.
     */
    public List<Role> getAllRoles() {
        TypedQuery<Role> q = em.createNamedQuery(Role.GET_ALL_ROLES, Role.class);
        return q.getResultList();
    }


    /**
     * Returns a user entity with the matching email wrapped in an optional.
     * If none exist, returns an empty Optional Object
     *
     * @param email : String containing the email.
     * @return : Optional, containing a user entity.
     */
    public Optional<User> getUserByEmail(@NotNull String email) {
        TypedQuery<User> q = em.createNamedQuery(User.GET_USER_BY_EMAIL, User.class)
                .setParameter("email", email);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


}