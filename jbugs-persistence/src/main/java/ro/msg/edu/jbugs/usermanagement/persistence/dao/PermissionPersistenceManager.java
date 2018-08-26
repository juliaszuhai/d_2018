package ro.msg.edu.jbugs.usermanagement.persistence.dao;

import ro.msg.edu.jbugs.usermanagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.Role;

import javax.ejb.Stateless;
import javax.persistence.*;
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


    /**
     * Updates the permission in the database with the one in the paramter
     *
     * @param permission - permission entity
     */
    public void updatePermission(Permission permission) {
        em.merge(permission);
    }


    /**
     * Removes the permission with the id from the parameter
     *
     * @param id - id of the permission to be removed
     * @return
     */
    public boolean removePermissionById(long id) {
        Optional<Permission> permissionOptional = getPermissionForId(id);
        permissionOptional.ifPresent(permOp ->
                getAllRoles().ifPresent(roleOp ->
                        roleOp.forEach(role ->
                                role.getPermissions().remove(permOp)
                        )
                )
        );
        if (!permissionOptional.isPresent())
            return false;
        em.remove(permissionOptional.get());
        return true;
    }


    /**
     * Removes a certain permission from a certain role.
     *
     * @param role
     * @param permission
     * @return true if it was removed, false otherwise.
     */
    public boolean removePermissionForRole(Role role, Permission permission) {
        return role.getPermissions().remove(permission);

    }


    public Optional<Permission> getPermissionForId(long id) {
        TypedQuery<Permission> query = em.createNamedQuery(Permission.GET_PERMISSION_BY_ID, Permission.class)
                .setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


    public Optional<List<Permission>> getPermissionsForRole(Role role) {
        TypedQuery<Permission> query = em.createNamedQuery(Permission.GET_PERMISSIONS_FOR_ROLE, Permission.class)
                .setParameter("role", role);
        return Optional.ofNullable(query.getResultList());

    }


    public List<Permission> getAllPermissions() {
        Query query = em.createQuery("SELECT p FROM Permission p");
        return query.getResultList();
    }


    public void createPermissionForRole(Role role, Permission permission) {
        role.getPermissions().add(permission);
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

    public Optional<List<Role>> getAllRoles() {
        TypedQuery<Role> q = em.createNamedQuery(Role.GET_ALL_ROLES, Role.class);
        return Optional.of(q.getResultList());
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
