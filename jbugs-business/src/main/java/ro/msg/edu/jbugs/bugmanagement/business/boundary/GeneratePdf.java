package ro.msg.edu.jbugs.bugmanagement.business.boundary;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import ro.msg.edu.jbugs.bugmanagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugmanagement.business.control.ExportBugPdf;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;



import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Path("/pdfbug")
public class GeneratePdf {

    @EJB
    private ExportBugPdf exportBugPdf;

    @EJB
    private BugManagement bugManagement;



    /**
     *Export a bug identified by an id into a pdf file
     *
     * @param id bug  on the view / edit page
     * @return Response with a pdf
     */
    @GET
//    @Secured("BUG_EXPORT_PDF")
    @Path("{id}")
    @Produces("application/pdf")
    public Response getFile(@PathParam("id") Long id) {
        File file = null;
        FileOutputStream fileOutputStream = null;
        String localDir = System.getProperty("user.dir");
        try {
            BugDTO bugDTO = bugManagement.getBugById(id);
            file = new File(localDir+"\\BugPdf.pdf");
            Document document = new Document();
            fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(document, fileOutputStream);
            exportBugPdf.createPdf(bugDTO, document);

            Response.ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition",
                    "attachment; filename=new-android-book.pdf");
            file.deleteOnExit();
            return response.build();

        } catch (DocumentException | BusinessException | IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}



