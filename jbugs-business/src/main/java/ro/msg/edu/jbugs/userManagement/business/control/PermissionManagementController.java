package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.userManagement.persistence.dao.PermissionPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.LinkedList;
import java.util.Optional;

@Stateless
public class PermissionManagementController {

    @EJB
    private PermissionPersistenceManager permissionPersistenceManager;

    @EJB
    private UserPersistenceManager userPersistenceManager;

    public void createPermission(Permission permission) {
        permissionPersistenceManager.createPermission(permission);
    }

    public void createRole(Role role) {
        permissionPersistenceManager.createRole(role);
    }


    /**
     * Adds a role to a user. If the role type does not exist it will automatically add the new role type to the database.
     * If the user  already has the role, this method will do nothing.
     * @param roleType
     * @param username
     */
    public void addRoleToUser(String roleType, String username) throws BusinessException {

        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        Role role = addRoleIfNotExists(roleType);
        if (!userOptional.isPresent()) {
            throw new BusinessException(ExceptionCode.USERNAME_NOT_VALID);
        } else {
            User user = userOptional.get();
            normalizeUserRoles(user);
            if (!user.getRoles().contains(role)) {
                userOptional.get().getRoles().add(role);
            }
        }

    }

    private void normalizeUserRoles(User user) {
        if (user.getRoles() == null) {
            user.setRoles(new LinkedList<>());
        }
    }

    private void normalizesRolePermissions(Role role) {
        if (role.getPermissions() == null) {
            role.setPermissions(new LinkedList<>());
        }
    }

    private Role addRoleIfNotExists(String roleType) {
        Optional<Role> roleOptional = permissionPersistenceManager.getRoleByType(roleType);
        Role role;
        if (!roleOptional.isPresent()) {
            role = new Role();
            role.setType("roleType");
            permissionPersistenceManager.createRole(role);
        } else {
            role = roleOptional.get();
        }
        return role;
    }

    private Permission addPermissionIfNotExists(String permissionType) {
        Optional<Permission> permissionOptional = permissionPersistenceManager.getPermissionByType(permissionType);

        Permission permission;
        if (!permissionOptional.isPresent()) {
            permission = new Permission();
            permission.setType(permissionType);
            permissionPersistenceManager.createPermission(permission);
        } else {
            permission = permissionOptional.get();
        }
        return permission;
    }

    /**
     * Adds a permission to a role. If the permission type or role type don't exist it will automatically add them to the database.
     * If the role already has the permission this method will do nothing.
     * @param permissionType
     * @param roleType
     */
    public void addPermissionToRole(String permissionType, String roleType) {

        Role role = addRoleIfNotExists(roleType);
        Permission permission = addPermissionIfNotExists(permissionType);
        normalizesRolePermissions(role);
        if (!role.getPermissions().contains(permission)) {
            role.getPermissions().add(permission);
        }
    }

    public void revokeRoleFromUser(String roleType, String username) {

    }
}
