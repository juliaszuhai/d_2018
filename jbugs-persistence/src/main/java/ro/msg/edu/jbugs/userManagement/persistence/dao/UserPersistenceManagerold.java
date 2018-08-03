package ro.msg.edu.jbugs.userManagement.persistence.dao;

import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Provides functions for working with users in the persistence layer.
 */
public interface UserPersistenceManagerold extends Serializable {

    /**
     * Persists a user in the database.
     * @param user not null
     * @return user - the user from the db
     */
    User createUser(@NotNull User user);

    /**
     * Updates a user from the database
     * @param user
     * @return updated user from the db
     */
    User updateUser(@NotNull User user);

    List<User> getAllUsers();

    /**
     *  Returns the user entity matching the username if it exists.
     *  TODO vedem
     * @param username
     * @return
     */
    User getUserByUsername(@NotNull String username);


    /**
     * Persists a role in the database.
     * @param role
     */
    void createRole(Role role);

    /**
     * Removes a role from the database.
     * @param role
     */
    void removeRole(Role role);

    /**
     * Updates a role in the database.
     * @param role
     * @return
     */
    Role updateRole(Role role);

    /**
     * TODO de scris
     * @param id
     * @return
     */
    Role getRoleForId(long id);

    /**
     * TODO de scris
     * @return
     */
    List<Role> getAllRoles();

    /**
     * TODO: de renovat
     * @param email
     * @return
     */
    List<User> getUserByEmail(@NotNull String email);

    Optional<User> getUserByEmail2(@NotNull String email);

    /**
     * TODO : change everything = should not be here.
     * @param username
     * @return list of usernames that start with username.
     */
    List<String> getUsernamesLike(@NotNull String username);
}
