package ro.msg.edu.jbugs.bugmanagement.business.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.dto.NameIdDTO;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class GenerateExcelTest {

    private ro.msg.edu.jbugs.bugManagement.business.boundary.GenerateExcel excel;

    @Test
    public void testGenerateExcel_ExpectedOK()
    {
        List<Long> ids=new ArrayList<Long>();
        ids.add(1L);
        NameIdDTO pers=new NameIdDTO();
        pers.setId(1L);
        pers.setUsername("Iulia");
        BugDTO bug1=new BugDTO();
        bug1.setId(1L);
        bug1.setTitle("Ceva");
        bug1.setDescription("Description1");
        bug1.setVersion("2.0.0");
        bug1.setStatus(Status.IN_PROGRESS);
        bug1.setSeverity(Severity.HIGH);
        bug1.setFixedVersion("1.0.0");
        bug1.setAssignedTo(pers);
        bug1.setCreatedByUser(pers);
        ////to be continued

    }
}
