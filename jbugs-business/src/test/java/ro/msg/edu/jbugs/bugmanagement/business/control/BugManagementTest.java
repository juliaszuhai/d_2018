package ro.msg.edu.jbugs.bugmanagement.business.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.dto.NameIdDTO;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.UserPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BugManagementTest {

    @InjectMocks
    private BugManagementController bugManagementController;

    @Mock
    private UserPersistenceManager userPersistenceManager;



    @Test
    public void getAllBugs_ExpectedOK() {
       /* User userUsed= new User();
        userUsed.setId(1L);
        userUsed.setUsername("ionion");
        when(userPersistenceManager.createUser(userUsed)).thenReturn(userUsed);
        BugDTO bug=new BugDTO();
        bug.setTitle("ceva");
        bug.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug.setVersion("1aa.2bb.3cc");
        bug.setFixedVersion("1.2.3");
        NameIdDTO user= new NameIdDTO();
        user.setId(1L);
        user.setUsername("ionion");
        bug.setAssignedTo(user);
        bug.setCreatedByUser(user);
        BugDTO bug2=new BugDTO();
        bug2.setTitle("ceva22");
        bug2.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug2.setVersion("1aa.2bb.3cc");
        bug2.setFixedVersion("1.2.3");
        bug2.setAssignedTo(user);
        bug2.setCreatedByUser(user);
        try {
            assertEquals(bug,bugManagementController.createBug(bug));
            assertEquals(bug2,bugManagementController.createBug(bug2));
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
        assertEquals(Arrays.asList(bug,bug2), bugManagementController.getAllBugs());*/

    }

    @Test
    public void getBugsWithId_ExpectedOK() {
        /*User userUsed= new User();
        userUsed.setId(1L);
        userUsed.setUsername("ionion");
        when(userPersistenceManager.createUser(userUsed)).thenReturn(userUsed);
        BugDTO bug=new BugDTO();
        bug.setTitle("ceva");
        bug.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug.setVersion("1aa.2bb.3cc");
        bug.setFixedVersion("1.2.3");
        NameIdDTO user= new NameIdDTO();
        user.setId(1L);
        user.setUsername("ionion");
        bug.setAssignedTo(user);
        bug.setCreatedByUser(user);
        BugDTO bug2=new BugDTO();
        bug2.setTitle("ceva22");
        bug2.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug2.setVersion("1aa.2bb.3cc");
        bug2.setFixedVersion("1.2.3");
        bug2.setAssignedTo(user);
        bug2.setCreatedByUser(user);
        try {
            assertEquals(bug,bugManagementController.createBug(bug));
            assertEquals(bug2,bugManagementController.createBug(bug2));
            assertEquals(bug,bugManagementController.getBugById(1L));
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }*/

    }

    @Test(expected = BusinessException.class)
    public void getBugById_ExpectedFail() {
        /*User userUsed= new User();
        userUsed.setId(1L);
        userUsed.setUsername("ionion");
        when(userPersistenceManager.createUser(userUsed)).thenReturn(userUsed);
        BugDTO bug=new BugDTO();
        bug.setTitle("ceva");
        bug.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug.setVersion("1aa.2bb.3cc");
        bug.setFixedVersion("1.2.3");
        NameIdDTO user= new NameIdDTO();
        user.setId(1L);
        user.setUsername("ionion");
        bug.setAssignedTo(user);
        bug.setCreatedByUser(user);
        BugDTO bug2=new BugDTO();
        bug2.setTitle("ceva22");
        bug2.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug2.setVersion("1aa.2bb.3cc");
        bug2.setFixedVersion("1.2.3");
        bug2.setAssignedTo(user);
        bug2.setCreatedByUser(user);
        try {
            assertEquals(bug,bugManagementController.createBug(bug));
            assertEquals(bug2,bugManagementController.createBug(bug2));

        } catch (BusinessException e) {
            fail("Should not reach this point");
        }*/
    }

    @Test
    public void createBug() {
        /*User userUsed= new User();
        userUsed.setId(1L);
        userUsed.setUsername("ionion");
        when(userPersistenceManager.createUser(userUsed)).thenReturn(userUsed);
        BugDTO bug=new BugDTO();
        bug.setTitle("ceva");
        bug.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug.setVersion("1aa.2bb.3cc");
        bug.setFixedVersion("1.2.3");
        NameIdDTO user= new NameIdDTO();
        user.setId(1L);
        user.setUsername("ionion");
        bug.setAssignedTo(user);
        bug.setCreatedByUser(user);
        try {
            assertEquals(bug, bugManagementController.createBug(bug));
        }catch (BusinessException e)
        {
            fail("Should not reach this point");
        }*/
    }

    @Test
    public void sort() {
        /*User userUsed= new User();
        userUsed.setId(1L);
        userUsed.setUsername("ionion");
        when(userPersistenceManager.createUser(userUsed)).thenReturn(userUsed);
        BugDTO bug=new BugDTO();
        bug.setTitle("ceva");
        bug.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug.setVersion("1aa.2bb.3cc");
        bug.setFixedVersion("1.2.3");
        NameIdDTO user= new NameIdDTO();
        user.setId(1L);
        user.setUsername("ionion");
        bug.setAssignedTo(user);
        bug.setCreatedByUser(user);
        BugDTO bug2=new BugDTO();
        bug2.setTitle("ceva22");
        bug2.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug2.setVersion("1aa.2bb.3cc");
        bug2.setFixedVersion("1.2.3");
        bug2.setAssignedTo(user);
        bug2.setCreatedByUser(user);
        try {
            assertEquals(bug, bugManagementController.createBug(bug));
            assertEquals(bug2, bugManagementController.createBug(bug2));
            assertEquals(Arrays.asList(bug,bug2),bugManagementController.sort(true,true));
        }catch(BusinessException e)
        {
            fail("Should not reach this point");
        }*/
    }


    @Test
    public void filter() {
        /*User userUsed= new User();
        userUsed.setId(1L);
        userUsed.setUsername("ionion");
        when(userPersistenceManager.createUser(userUsed)).thenReturn(userUsed);
        BugDTO bug=new BugDTO();
        bug.setTitle("ceva");
        bug.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug.setVersion("1aa.2bb.3cc");
        bug.setFixedVersion("1.2.3");
        bug.setSeverity(Severity.HIGH);
        bug.setStatus(Status.IN_PROGRESS);
        NameIdDTO user= new NameIdDTO();
        user.setId(1L);
        user.setUsername("ionion");
        bug.setAssignedTo(user);
        bug.setCreatedByUser(user);
        BugDTO bug2=new BugDTO();
        bug2.setTitle("ceva22");
        bug2.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug2.setVersion("1aa.2bb.3cc");
        bug2.setFixedVersion("1.2.3");
        bug2.setAssignedTo(user);
        bug2.setCreatedByUser(user);
        try {
            assertEquals(bug, bugManagementController.createBug(bug));
            assertEquals(bug2, bugManagementController.createBug(bug2));
            assertEquals(Arrays.asList(bug),bugManagementController.filter("ceva","",Status.IN_PROGRESS,Severity.HIGH));
        }catch(BusinessException e)
        {
            fail("Should not reach this point");
        }*/
    }

    @Test(expected = BusinessException.class)
    public void testvalidateDescription_ExpectedException() throws BusinessException {
        when(bugManagementController.validateDescription("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascet")).thenThrow(BusinessException.class);
    }

    @Test
    public void testValidateVersion_ExpectedOK() {
        try {
            assertTrue( bugManagementController.validateVersion("1ccc.2bbb.3aaa"));
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test(expected = BusinessException.class)
    public void testValidateVersion_ExpectedException() throws BusinessException {
        when(bugManagementController.validateVersion("123.")).thenThrow(BusinessException.class);
    }



    @Test
    public void testValidateBug_ExpectedOK()  {
        Bug bug=new Bug();
        bug.setTitle("ceva");
        bug.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug.setVersion("1aa.2bb.3cc");
        bug.setFixedVersion("1.2.3");
        bug.setTargetDate(new Date());
        bug.setSeverity(Severity.HIGH);
        bug.setStatus(Status.IN_PROGRESS);
        User user=new User();
        user.setFirstName("Ion");
        user.setLastName("Ion");
        user.setEmail("quer@msggroup.com");
        user.setPassword("Parola12.34");
        user.setPhoneNumber("0002220001");
        bug.setAssignedTo(user);
        bug.setCreatedByUser(user);
        try {
            assertTrue(bugManagementController.isBugValid(bug));
        } catch (BusinessException e) {
            e.printStackTrace();
        }

    }

    @Test(expected = BusinessException.class)
    public void testValidateBug_ExpectedFail() throws BusinessException {
        Bug bug=new Bug();
        bug.setTitle("ceva");
        bug.setDescription("of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug.setVersion("1");
        bug.setFixedVersion("1.2.3");
        bug.setTargetDate(new Date());
        bug.setSeverity(Severity.HIGH);
        bug.setStatus(Status.IN_PROGRESS);
        User user=new User();
        user.setFirstName("Ion");
        user.setLastName("Ion");
        user.setEmail("quer@msggroup.com");
        user.setPassword("Parola12.34");
        user.setPhoneNumber("0002220001");
        bug.setAssignedTo(user);
        bug.setCreatedByUser(user);
        when(bugManagementController.isBugValid(bug)).thenThrow(BusinessException.class);

    }

    @Test
    public void testvalidateDescription_ExpectedOK() {
        try {
            assertEquals(true, bugManagementController.validateDescription("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium q"));
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }
}