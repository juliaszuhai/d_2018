package ro.msg.edu.jbugs.bugManagement.business.control;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.bugManagement.persistence.dao.BugPersistenceManager;

@RunWith(MockitoJUnitRunner.class)
public class BugPersistenceManagerBeanTest {

    @InjectMocks
    private BugManagementController bugManagementController;

    @Mock
    private BugPersistenceManager bugPersistenceManager;

}
