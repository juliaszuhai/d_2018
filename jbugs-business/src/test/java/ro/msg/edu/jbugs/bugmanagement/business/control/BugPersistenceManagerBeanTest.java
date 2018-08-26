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

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BugPersistenceManagerBeanTest {

    @Mock
    private BugPersistenceManager bugPersistenceManager;


    @Test
    public void testGetAllBugs_ExpectedOK() {
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
        Bug bug2=new Bug();
        bug2.setTitle("ceva22");
        bug2.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug2.setVersion("1aa.2bb.3cc");
        bug2.setFixedVersion("1.2.3");
        bug2.setTargetDate(new Date());
        bug2.setSeverity(Severity.HIGH);
        bug2.setStatus(Status.IN_PROGRESS);
        bug2.setAssignedTo(user);
        bug2.setCreatedByUser(user);
        bugPersistenceManager.createBug(bug);
        bugPersistenceManager.createBug(bug2);
        when(bugPersistenceManager.getAllBugs())
                .thenReturn(Arrays.asList(bug,bug2));
        }

    @Test
    public void getBugById() {
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
        bugPersistenceManager.createBug(bug);
        when(bugPersistenceManager.getBugById(1L)).thenReturn(Optional.of(bug));
    }

    @Test
    public void createBug() {
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
        when(bugPersistenceManager.createBug(bug)).thenReturn(bug);
    }

    @Test
    public void filter() {
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
        Bug bug2=new Bug();
        bug2.setTitle("ceva2");
        bug2.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug2.setVersion("1aa.2bb.3cc");
        bug2.setFixedVersion("1.2.3");
        bug2.setTargetDate(new Date());
        bug2.setSeverity(Severity.HIGH);
        bug2.setStatus(Status.IN_PROGRESS);
        bug2.setAssignedTo(user);
        bug2.setCreatedByUser(user);
        bugPersistenceManager.createBug(bug);
        bugPersistenceManager.createBug(bug2);
        when(bugPersistenceManager.filter("ceva","A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha",Status.IN_PROGRESS,Severity.HIGH)).thenReturn(Arrays.asList(bug));
    }

    @Test
    public void sort() {
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
        Bug bug2=new Bug();
        bug2.setTitle("ceva2");
        bug2.setDescription("A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so ha");
        bug2.setVersion("1aa.2bb.3cc");
        bug2.setFixedVersion("1.2.3");
        bug2.setTargetDate(new Date());
        bug2.setSeverity(Severity.HIGH);
        bug2.setStatus(Status.IN_PROGRESS);
        bug2.setAssignedTo(user);
        bug2.setCreatedByUser(user);
        bugPersistenceManager.createBug(bug);
        bugPersistenceManager.createBug(bug2);
        when(bugPersistenceManager.sort(true,true)).thenReturn(Arrays.asList(bug,bug2));
    }
}
