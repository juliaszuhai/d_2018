package ro.msg.edu.jbugs.usermanagement.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.notificationmanagement.business.service.NotificationManagementService;
import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.TypeNotification;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.PermissionPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.Permission;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.Role;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PermissionManagementServiceTest {

    @Mock
    private PermissionPersistenceManager permissionPersistenceManager;

    @Mock
    private UserPersistenceManager userPersistenceManager;

    @Mock
    private NotificationManagementService notificationManagementService;

    @InjectMocks
    private PermissionManagementService permissionManagementController;


    @Test
    public void createRole_ExpectedOK() {
        Permission permission = new Permission();
        permission.setType("USER_MANAGEMENT");
        permission.setDescription("Can add a new user");
        PermissionPersistenceManager permissionPersistenceManagerr = mock(PermissionPersistenceManager.class);
        doNothing().when(permissionPersistenceManagerr).createPermission(permission);
        Role role = new Role();
        role.setType("ADM");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        when(permissionPersistenceManagerr.createRole(role)).thenReturn(new Role());
        permissionManagementController.createRole(role);
    }

    @Test
    public void addRoleToUser_ExpectedOK() {
        User user =  new User();
        user.setFirstName("Mihai");
        user.setLastName("Tantarean");
        user.setEmail("mihaitudor@msggroup.com");
        user.setPassword("Parola12.34");
		user.setUsername("tantam");
        when(userPersistenceManager.createUser(user)).thenReturn(user);
        when(userPersistenceManager.getUserById(1L)).thenReturn(Optional.of(user));
        Permission permission = new Permission();
        permission.setType("USER_MANAGEMENT");
        permission.setDescription("Can add a new user");
        PermissionPersistenceManager permissionPersistenceManagerr = mock(PermissionPersistenceManager.class);
        doNothing().when(permissionPersistenceManagerr).createPermission(permission);
        Role role = new Role();
        role.setType("ADM");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        when(permissionPersistenceManagerr.createRole(role)).thenReturn(new Role());
        when(permissionPersistenceManager.getRoleByType(role.getType())).thenReturn(Optional.of(role));
        when(permissionPersistenceManager.getRoleByType("ADM")).thenReturn(Optional.of(role));
        when(userPersistenceManager.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        doNothing().when(notificationManagementService).sendNotification(
                any(TypeNotification.class),
                any(Object.class),
                any(Object.class),
                any(List.class));
        try {
            permissionManagementController.addRoleToUser("ADM", user.getUsername(), user.getUsername());
        } catch (BusinessException e) {
            fail("Not exist the username");
        }
    }

    @Test
    public void addRoleToUser_ExpectedException() {
        User user =  new User();
        user.setFirstName("Mihai");
        user.setLastName("Tantarean");
        user.setEmail("mihaitudor@msggroup.com");
        user.setPassword("Parola12.34");
        when(userPersistenceManager.createUser(user)).thenReturn(user);
        Permission permission = new Permission();
        permission.setType("USER_MANAGEMENT");
        permission.setDescription("Can add a new user");
        PermissionPersistenceManager permissionPersistenceManagerr = mock(PermissionPersistenceManager.class);
        doNothing().when(permissionPersistenceManagerr).createPermission(permission);
        Role role = new Role();
        role.setType("ADM");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        when(permissionPersistenceManagerr.createRole(role)).thenReturn(new Role());
        when(permissionPersistenceManager.getRoleByType(role.getType())).thenReturn(Optional.of(role));
        when(permissionPersistenceManager.getRoleByType("ADM")).thenReturn(Optional.of(role));
        when(userPersistenceManager.getUserByUsername(user.getUsername())).thenReturn(Optional.empty());
        doNothing().when(notificationManagementService).sendNotification(
                any(TypeNotification.class),
                any(Object.class),
                any(Object.class),
                any(List.class));
        try {
			permissionManagementController.addRoleToUser("ADM", user.getUsername(), user.getUsername());
            fail("Not exist the username");
        }catch (BusinessException e){
            assertEquals(ExceptionCode.USERNAME_NOT_VALID,e.getExceptionCode());
        }
    }

    @Test
    public void addPermissionToRole() {
    }

    @Test
    public void revokeRoleFromUser_ExpectedOK() {
        User user =  new User();
        user.setFirstName("Mihai");
        user.setLastName("Tantarean");
        user.setEmail("mihaitudor@msggroup.com");
        user.setPassword("Parola12.34");
        when(userPersistenceManager.createUser(user)).thenReturn(user);
        when(userPersistenceManager.getUserById(1L)).thenReturn(Optional.of(user));
        Permission permission = new Permission();
        permission.setType("USER_MANAGEMENT");
        permission.setDescription("Can add a new user");
        PermissionPersistenceManager permissionPersistenceManagerr = mock(PermissionPersistenceManager.class);
        doNothing().when(permissionPersistenceManagerr).createPermission(permission);
        Role role = new Role();
        role.setType("ADM");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        when(permissionPersistenceManagerr.createRole(role)).thenReturn(new Role());
        when(permissionPersistenceManager.getRoleByType(role.getType())).thenReturn(Optional.of(role));
        when(permissionPersistenceManager.getRoleByType("ADM")).thenReturn(Optional.of(role));
        when(userPersistenceManager.getUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        try {
            permissionManagementController.addRoleToUser("ADM", user.getUsername(), user.getUsername());
            when(permissionPersistenceManager.getRoleByType("ADM")).thenReturn(Optional.of(role));
            permissionManagementController.revokeRoleFromUser(role.getType(), user.getUsername(), user.getUsername());
            assertEquals(new ArrayList<>(), user.getRoles());
        } catch (BusinessException e) {
            fail(" the user or role type not exist");
        }

    }


    @Test
    public void revokeRoleFromUser_ExpectedException() {
        User user =  new User();
        user.setFirstName("Mihai");
        user.setLastName("Tantarean");
        user.setEmail("mihaitudor@msggroup.com");
        user.setPassword("Parola12.34");
        when(userPersistenceManager.createUser(user)).thenReturn(user);
        when(userPersistenceManager.getUserById(1L)).thenReturn(Optional.of(user));
        when(userPersistenceManager.getUserById(1L)).thenReturn(Optional.empty());
        Permission permission = new Permission();
        permission.setType("USER_MANAGEMENT");
        permission.setDescription("Can add a new user");
        PermissionPersistenceManager permissionPersistenceManagerr = mock(PermissionPersistenceManager.class);
        doNothing().when(permissionPersistenceManagerr).createPermission(permission);
        Role role = new Role();
        role.setType("ADM");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        when(permissionPersistenceManagerr.createRole(role)).thenReturn(new Role());
        when(permissionPersistenceManager.getRoleByType(role.getType())).thenReturn(Optional.of(role));
        when(permissionPersistenceManager.getRoleByType("ADM")).thenReturn(Optional.of(role));
        when(userPersistenceManager.getUserByUsername(user.getUsername())).thenReturn(Optional.empty());
        try {
			permissionManagementController.addRoleToUser("ADM", user.getUsername(), user.getUsername());
            when(permissionPersistenceManager.getRoleByType("ADM")).thenReturn(Optional.empty());
			permissionManagementController.revokeRoleFromUser(role.getType(), null, user.getUsername());
           fail("The user does not exist");
        }catch (BusinessException e){
            assertEquals(ExceptionCode.USERNAME_NOT_VALID,e.getExceptionCode());
        }

    }

    @Test
    public void revokePermissionFromRole_ExpectedOK() {
        Permission permission = new Permission();
        permission.setType("USER_MANAGEMENT");
        permission.setDescription("Can add a new user");
        doNothing().when(permissionPersistenceManager).createPermission(permission);
        Role role = new Role();
        role.setType("ADM");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        when(permissionPersistenceManager.createRole(role)).thenReturn(new Role());
        when(permissionPersistenceManager.getRoleByType(role.getType())).thenReturn(Optional.of(role));
        when(permissionPersistenceManager.getPermissionByType(permission.getType())).thenReturn(Optional.of(permission));
        permissionManagementController.revokePermissionFromRole(role.getType(),permission.getType());
    }


    @Test
    public void getPermissionByRole_ExpectedOK() {
        Permission permission = new Permission();
        permission.setType("USER_MANAGEMENT");
        permission.setDescription("Can add a new user");
        PermissionPersistenceManager permissionPersistenceManagerr = mock(PermissionPersistenceManager.class);
        doNothing().when(permissionPersistenceManagerr).createPermission(permission);
        Role role = new Role();
        role.setType("ADM");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        when(permissionPersistenceManagerr.createRole(role)).thenReturn(new Role());
        when(permissionPersistenceManager.getRoleByType(role.getType())).thenReturn(Optional.of(role));
        try {
            assertEquals(Arrays.asList(permission), permissionManagementController.getPermissionsByRole(role.getType()));
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void getPermissionByRole_ExpectedException(){
        Permission permission= new Permission();
        permission.setType("BUG_MANAGEMENT");
        permission.setDescription("Can add a new user");
        doNothing().when(permissionPersistenceManager).createPermission(permission);
        Role role = new Role();
        role.setType("ADM");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permission);
        role.setPermissions(permissions);
        when(permissionPersistenceManager.createRole(role)).thenReturn(role);
        when(permissionPersistenceManager.getRoleByType("USER")).thenReturn(Optional.empty());
        try{
            permissionManagementController.getPermissionsByRole("USER");
            fail("Should not reach this point");
        }catch(BusinessException e){
          assertEquals(ExceptionCode.ROLE_DOESNT_EXIST,e.getExceptionCode());
        }
    }

    @Test
    public void getAllPermissions_ExpectedOK() {
        Permission permissionBug = new Permission();
        permissionBug.setType("BUG_MANAGEMENT");
        permissionBug.setDescription("Can view all bugs");
        doNothing().when(permissionPersistenceManager).createPermission(permissionBug);
        Permission permissionUser = new Permission();
        permissionUser.setType("User");
        permissionUser.setDescription("Can add a new user");
        doNothing().when(permissionPersistenceManager).createPermission(permissionUser);
        when(permissionPersistenceManager.getAllPermissions()).thenReturn(Arrays.asList(permissionBug, permissionUser));
        assertEquals(Arrays.asList(permissionBug, permissionUser), permissionManagementController.getAllPermissions());
    }

    @Test
    public void getAllRoles_ExpectedOk() {
        Permission permissionBug = new Permission();
        permissionBug.setType("BUG_MANAGEMENT");
        permissionBug.setDescription("Can view all bugs");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permissionBug);
        Role roleAdm = new Role();
        roleAdm.setPermissions(permissions);
        roleAdm.setType("ADM");
        when(permissionPersistenceManager.createRole(roleAdm)).thenReturn(roleAdm);
        Permission permissionUser = new Permission();
        permissionUser.setType("USER_MANAGEMENT");
        permissionUser.setDescription("Can add a user");
        List<Permission> lPermissions = new ArrayList<>();
        lPermissions.add(permissionUser);
        Role roleUser = new Role();
        roleUser.setPermissions(permissions);
        roleUser.setType("USER");
        when(permissionPersistenceManager.createRole(roleUser)).thenReturn(roleUser);
        when(permissionPersistenceManager.getAllRoles()).thenReturn(Arrays.asList(roleAdm, roleUser));
        assertEquals(Arrays.asList(roleAdm, roleUser), permissionManagementController.getAllRoles());
    }



}