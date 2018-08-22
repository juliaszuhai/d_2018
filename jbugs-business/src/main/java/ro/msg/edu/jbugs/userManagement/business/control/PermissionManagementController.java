package ro.msg.edu.jbugs.userManagement.business.control;

import ro.msg.edu.jbugs.userManagement.persistence.dao.PermissionPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class PermissionManagementController {

@EJB
private PermissionPersistenceManager permissionPersistenceManager;

@EJB
private UserPersistenceManager userPersistenceManager;

    public void createPermission(Permission permission){
        permissionPersistenceManager.createPermission(permission);
    }

    public void createRole(Role role){
        permissionPersistenceManager.createRole(role);
    }

    public void addRoleToUser(String roleType, String username){
        Optional<Role> roleOptional = permissionPersistenceManager.getRoleByType(roleType);
        Optional<User> userOptional = userPersistenceManager.getUserByUsername(username);
        Role role;
        if(!roleOptional.isPresent()){
            role = new Role();
            role.setType("roleType");
            permissionPersistenceManager.createRole(role);
        } else {
            role = roleOptional.get();
        }

        if(roleOptional.isPresent() && userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getRoles()==null){
                user.setRoles(new ArrayList<>());
            }
            user.getRoles().add(role);
        }

        //TODO add exceptions
    }

    public void addPermissionToRole(String permissionType,String roleType){
        Optional<Role> roleOptional = permissionPersistenceManager.getRoleByType(roleType);
        Optional<Permission> permissionOptional = permissionPersistenceManager.getPermissionByType(permissionType);
        Role role;
        Permission permission;
        if(!roleOptional.isPresent()){
            role = new Role();
            role.setType(roleType);
            permissionPersistenceManager.createRole(role);
        } else{
            role = roleOptional.get();
        }
        if(!permissionOptional.isPresent()){
            permission = new Permission();
            permission.setType(permissionType);
            permissionPersistenceManager.createPermission(permission);
        } else{
            permission = permissionOptional.get();
        }

        if(roleOptional.isPresent() && permissionOptional.isPresent()){
            if(role.getPermissions()==null){
                role.setPermissions(new ArrayList<>());
            }
            role.getPermissions().add(permission);
        }

        //TODO add exceptions
    }
}
