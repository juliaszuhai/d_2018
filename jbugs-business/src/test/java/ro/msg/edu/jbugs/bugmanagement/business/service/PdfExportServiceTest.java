package ro.msg.edu.jbugs.bugmanagement.business.service;

import com.itextpdf.text.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.dto.NameIdDTO;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;

import java.util.Date;

import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class PdfExportServiceTest {

    @InjectMocks
    PdfExportService pdfExportService;


    @Test
    public void createPdf() {
        try{
            NameIdDTO user= new NameIdDTO();
            user.setId(1L);
            user.setUsername("ionion");
            BugDTO bugDTO=new BugDTO();
            bugDTO.setTargetDate(new Date());
            bugDTO.setTitle("Bug Nou");
            bugDTO.setDescription("something");
            bugDTO.setStatus(Status.IN_PROGRESS);
            bugDTO.setSeverity(Severity.HIGH);
            bugDTO.setFixedVersion("1.2.3");
            bugDTO.setVersion("1.2.4");
            bugDTO.setCreatedByUser(user);
            bugDTO.setAssignedTo(user);
            Document document = new Document();
            pdfExportService.createPdf(bugDTO,document);
        }catch (BusinessException e)
        {
            fail("Shouldn't reach here");
        }
    }

    @Test
    public void getCell() {
    }
}