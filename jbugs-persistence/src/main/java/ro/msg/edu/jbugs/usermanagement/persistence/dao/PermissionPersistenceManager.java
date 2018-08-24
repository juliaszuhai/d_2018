package ro.msg.edu.jbugs.usermanagement.persistence.dao;

import ro.msg.edu.jbugs.usermanagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class PermissionPersistenceManager {

    //private static final Logger logger = LogManager.getLogger(PermissionPersistenceManager.class);

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;






    public Permission createPermission(Permission permission) {

        em.persist(permission);
        return permission;
    }


    public Permission updatePermission(Permission permission) {
        em.merge(permission);
        return permission;
    }


    public boolean removePermissionById(long id) {
        Permission permission = getPermissionForId(id);
        if(permission == null)
            return false;
        em.remove(permission);
        return true;
    }




    public boolean removePermissionForRole(Role role, Permission permission) {
        em.persist(role);
        List<Permission> permissions = getPermissionsForRole(role);
        return permissions.remove(permission);

    }


    public boolean removeAllPermissionsForRole(Role role) {
        em.persist(role);
        List<Permission> permissions = getPermissionsForRole(role);
        permissions.clear();
        return true;
    }


    public Permission getPermissionForId(long id) {
        Query query = em.createQuery("SELECT p FROM Permission p WHERE p.id=:id");
        query.setParameter("id",id);
        return (Permission) query.getSingleResult();
    }


    public List<Permission> getPermissionsForRole(Role role) {
        Query query = em.createQuery("SELECT r.permissions FROM Role r WHERE r=:role");
        query.setParameter("role",role);
        return query.getResultList();

    }


    public List<Permission> getAllPermissions() {
        Query query = em.createQuery("SELECT p FROM Permission p");
        return query.getResultList();
    }


    public Permission createPermissionForRole(Role role, Permission permission) {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        em.merge(role);
        return permission;
    }

    public Role createRole(Role role){
        em.persist(role);
        return role;
    }

    public Optional<Role> getRoleByType(String type){
        TypedQuery<Role> query = em.createNamedQuery(Role.GET_ROLE_BY_TYPE,Role.class)
                .setParameter("type", type);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<Permission> getPermissionByType(String type){
        TypedQuery<Permission> query = em.createNamedQuery(Permission.GET_PERMISSION_BY_TYPE,Permission.class)
                .setParameter("type", type);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


}
