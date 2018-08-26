package ro.msg.edu.jbugs.bugmanagement.business.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.persistence.dao.BugPersistenceManager;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class BugPersistenceManagerBeanTest {

    @InjectMocks
    private BugManagementController bugManagementController;

    @Mock
    private BugPersistenceManager bugPersistenceManager;


    @Test
    public void testvalidateDescription_ExpectedOK() {
        try {
            assertEquals(true, bugManagementController.validateDescription("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium q"));
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test(expected = BusinessException.class)
    public void testvalidateDescription_ExpectedException() throws BusinessException {
        bugManagementController.validateDescription("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascet");
    }

    @Test
    public void testValidateVersion_ExpectedOK() {
        try {
            assertEquals(true, bugManagementController.validateVersion("1ccc.2bbb.3aaa"));
        } catch (BusinessException e) {
            fail("Should not reach this point");
        }
    }

    @Test(expected = BusinessException.class)
    public void testValidateVersion_ExpectedException() throws BusinessException {
        bugManagementController.validateVersion("123.");
    }



    @Test
    public void testValidateBug_ExpectedOK() {
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
            assertEquals(true,bugManagementController.isBugValid(bug));
        } catch (BusinessException e) {
            fail("Should not reach this point");
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
        bugManagementController.isBugValid(bug);

    }

}
