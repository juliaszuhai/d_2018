package ro.msg.edu.jbugs.usermanagement.persistence.dao;

import ro.msg.edu.jbugs.usermanagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Stateless
public class PermissionPersistenceManager {


    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;


    /**
     * Persists the permission from the parameter in the database
     *
     * @param permission - permission entity
     */
    public void createPermission(Permission permission) {
        em.persist(permission);
    }



    public List<Permission> getAllPermissions() {
        TypedQuery<Permission> query = em.createNamedQuery(Permission.GET_ALL_PERMISSIONS, Permission.class);
        return query.getResultList();
    }



    public Role createRole(Role role) {
        em.persist(role);
        return role;
    }

    public Optional<Role> getRoleByType(String type) {
        TypedQuery<Role> query = em.createNamedQuery(Role.GET_ROLE_BY_TYPE, Role.class)
                .setParameter("type", type);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Role> getAllRoles() {
        TypedQuery<Role> q = em.createNamedQuery(Role.GET_ALL_ROLES, Role.class);
        return q.getResultList();
    }

    public Optional<Permission> getPermissionByType(String type) {
        TypedQuery<Permission> query = em.createNamedQuery(Permission.GET_PERMISSION_BY_TYPE, Permission.class)
                .setParameter("type", type);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


}
