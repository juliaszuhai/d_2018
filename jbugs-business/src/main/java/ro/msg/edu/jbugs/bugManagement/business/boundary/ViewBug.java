package ro.msg.edu.jbugs.bugManagement.business.boundary;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.control.ExportBugPdf;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.BusinessException;


import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Path("/viewBug")
public class ViewBug {

    @EJB
    private ExportBugPdf exportBugPdf;

    @EJB
    private BugManagement bugManagement;

    private static final String FILE_PATH = "t:/BugPdf.pdf";


    /**
     * Export the bug which have the specify title in a pdf
     *
     * @param id
     * @return Response with a pdf
     */
    @GET
    @Path("{id}")
    @Produces("application/pdf")
    public Response getFile(@PathParam("id") Long id) {
        File file = null;
        FileOutputStream fileOutputStream = null;

        try {
            BugDTO bugDTO = bugManagement.getBugById(id);
            file = new File(FILE_PATH);
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
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}



