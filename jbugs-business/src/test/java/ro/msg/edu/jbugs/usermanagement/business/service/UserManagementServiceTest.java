package ro.msg.edu.jbugs.usermanagement.business.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.bugmanagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;
import ro.msg.edu.jbugs.usermanagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.usermanagement.business.utils.Encryptor;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.PermissionPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.Role;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementServiceTest {


    @InjectMocks
    private UserManagementService userManagementController;

    @Mock
    private UserPersistenceManager userPersistenceManager;

    @Mock
    private BugPersistenceManager bugPersistenceManager;

	@Mock
    private PermissionPersistenceManager permissionPersistenceManager;



    @Test
    public void testGenerateUsername_ExpectedOK() {
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());

        assertEquals("iftind", userManagementController.generateUsername("Dan", "Iftinca"));
    }

    @Test
    public void testGenerateUsername_Expectedmarini() {
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());

        assertEquals("marini", userManagementController.generateUsername("Ion", "Marin"));
    }

    @Test
    public void testGenerateUsername_Expectedionion() {
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());

        assertEquals("ionion", userManagementController.generateUsername("Ion", "Ion"));
    }

    @Test
    public void testGenerateUsername_Expectedba0000() {
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());

        assertEquals("ba0000", userManagementController.generateUsername("a", "b"));
    }

    @Test
    public void testGenerateUsername_Expected() {
        User mockUser = new User();
        mockUser.setUsername("marini");
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());
        when(userPersistenceManager.getUserByUsername("marini"))
                .thenReturn(Optional.of(mockUser));
        when(userPersistenceManager.getUserByUsername("mariio"))
                .thenReturn(Optional.empty());
        assertEquals("mariio", userManagementController.generateUsername("Ion", "Marin"));
    }


    @Test
    public void testLogin_wrongUsername() {
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());
        try {
            userManagementController.login("a", "s");
            fail("Shouldn't reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.USERNAME_NOT_VALID, e.getExceptionCode());
        }
    }

    @Test
    public void testLogin_Success() {
        User user = mock(User.class);
        when(user.getUsername()).thenReturn("salut");
        when(user.getPassword()).thenReturn(Encryptor.encrypt("secret"));

        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.of(user));

        when(user.getActive()).thenReturn(true);


        try {
            UserDTO userDTO = userManagementController.login("salut", "secret");
            assertEquals(userDTO.getUsername(), user.getUsername());
        } catch (BusinessException e) {
            fail("Shouldn't reach this point: " + e.getExceptionCode().getMessage());
        }
    }

    @Test
    public void testCreateUser_Success() {
        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.empty());

        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());


        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Cristi");
        userDTO.setLastName("Borcea");
        userDTO.setEmail("dinamo@msggroup.com");
        userDTO.setPhoneNumber("0747046000");
        userDTO.setPassword("IloveSteaua");

//        try {
//            when(NotificationDTOHelper.toEntity(any(NotificationDTO.class)))
//                    .thenReturn(new Notification());
//        } catch (ParseException e) {
//            fail("shouldn't reach this");
//        }
        try {

            UserDTO createdUser = userManagementController.createUser(userDTO);
            assertEquals(userDTO.getFirstName(), createdUser.getFirstName());
            assertEquals(userDTO.getLastName(), createdUser.getLastName());
            assertEquals(userDTO.getEmail(), createdUser.getEmail());
            assertEquals("borcec", createdUser.getUsername());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void testCreateUser_ValidationException() {
        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.empty());

        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Cristi");
        userDTO.setLastName("Borcea");
        userDTO.setEmail("dinamo");
        userDTO.setPhoneNumber("asdf");
        userDTO.setPassword("IloveSteaua");
        try {
            userManagementController.createUser(userDTO);
            fail("Shouldn't reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.USER_VALIDATION_EXCEPTION, e.getExceptionCode());
        }

    }

    @Test
    public void testCreateUser_EmailException() {
        when(userPersistenceManager.getUserByEmail(any(String.class)))
                .thenReturn(Optional.of(new User()));

        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Cristi");
        userDTO.setLastName("Borcea");
        userDTO.setEmail("dinamo@msggroup.com");
        userDTO.setPhoneNumber("0747046000");
        userDTO.setPassword("IloveSteaua");
        try {
            userManagementController.createUser(userDTO);
            fail("Shouldn't reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.EMAIL_EXISTS_ALREADY, e.getExceptionCode());
        }
    }

    @Test
    public void deactivateUser_ExpectedOK() {
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.of(new User()));

        when(bugPersistenceManager.getAllBugs())
                .thenReturn(new ArrayList<>());

        when(permissionPersistenceManager.getRoleByType(any(String.class)))
                .thenReturn(Optional.of(new Role()));
        try {
            userManagementController.deactivateUser("dorel");
        } catch (BusinessException e) {
            fail("Should not reach this point" + e.getExceptionCode().getMessage());
        }

    }

    @Test
    public void deactivateUser_UserHasBugs() {
        User user = new User();
        user.setUsername("doreld");
        Bug bug1 = new Bug();
        bug1.setAssignedTo(user);
        bug1.setStatus(Status.NEW);
        bug1.setCreatedByUser(user);
        List<Bug> bugList = new LinkedList<Bug>();
        bugList.add(bug1);
        when(bugPersistenceManager.getAllBugs())
                .thenReturn(bugList);
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.of(user));


        try {
            userManagementController.deactivateUser("doreld");
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.USER_HAS_ASSIGNED_BUGS, e.getExceptionCode());
        }
    }

    @Test
    public void deactivateUser_Expected_UserNameNotValid() {
        User user = new User();
        user.setUsername("doreld");
        Bug bug1 = new Bug();
        bug1.setAssignedTo(user);
        bug1.setStatus(Status.NEW);
        bug1.setCreatedByUser(user);
        List<Bug> bugList = new LinkedList<Bug>();
        when(bugPersistenceManager.getAllBugs())
                .thenReturn(bugList);
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());


        try {
            userManagementController.deactivateUser("doreld");
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.USERNAME_NOT_VALID, e.getExceptionCode());
        }
    }

    @Test
    public void activateUser_ExpectedOk() {
        User user = new User();
        user.setUsername("doreld");
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.of(user));
        doNothing().when(userPersistenceManager).updateUser(user);
        try {
            userManagementController.activateUser("doreld");

        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void activateUser_ExpectedException() {
        User user = new User();
        user.setUsername("doreld");
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());
        when(userPersistenceManager.getUserByUsername("doreld"))
                .thenReturn(Optional.of(user));
        doNothing().when(userPersistenceManager).updateUser(user);
        try {
            userManagementController.activateUser("asdf");

            fail("Should not reach this point");
        } catch (BusinessException e) {

            assertEquals(ExceptionCode.USERNAME_NOT_VALID, e.getExceptionCode());
        }
    }

    @Test
    public void getAllUsers_ExpectedOk() {
        User user = new User();
        user.setUsername("doreld");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userPersistenceManager.getAllUsers()).thenReturn(userList);
        assertEquals(1, userManagementController.getAllUsers().size());
    }

    @Test
    public void getUserForUsername_ExpectedOk() {
        User dorel = new User();
        dorel.setUsername("dorel");
        when(userPersistenceManager.getUserByUsername("dorel"))
                .thenReturn(Optional.of(dorel));

        try {
            assertEquals("dorel", userManagementController.getUserForUsername("dorel").getUsername());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void getUserForUsername_ExpectedException() {
        User dorel = new User();
        dorel.setUsername("dorel");
        when(userPersistenceManager.getUserByUsername("dorel"))
                .thenReturn(Optional.of(dorel));
        when(userPersistenceManager.getUserByUsername(any(String.class)))
                .thenReturn(Optional.empty());

        try {
            userManagementController.getUserForUsername("asdf");
            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.USERNAME_NOT_VALID, e.getExceptionCode());
        }
    }

    @Test
    public void getUserForId_ExpectedOk() {
        User dorel = new User();
        dorel.setUsername("dorel");
        dorel.setId(1L);
        when(userPersistenceManager.getUserById(1L))
                .thenReturn(Optional.of(dorel));

        try {
            assertEquals("dorel", userManagementController.getUserForId(1L).getUsername());
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test
    public void getUserForId_ExpectedException() {
        User dorel = new User();
        dorel.setUsername("dorel");
        dorel.setId(1L);
        when(userPersistenceManager.getUserById(1L))
                .thenReturn(Optional.of(dorel));
        when(userPersistenceManager.getUserById(any(Long.class)))
                .thenReturn(Optional.empty());

        try {
            userManagementController.getUserForId(2L);

            fail("Should not reach this point");
        } catch (BusinessException e) {
            assertEquals(ExceptionCode.USERNAME_NOT_VALID, e.getExceptionCode());
        }
    }


}