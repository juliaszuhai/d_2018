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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


@Path("/viewBug")
public class ViewBug {

    @EJB
    private ExportBugPdf exportBugPdf;

    @EJB
    private BugManagement bugManagement;

    private static final String FILE_PATH = "t:/BugPdf.pdf";

    /**
     * Export the bug which have the specify title in a pdf
     * @param title
     * @return Response
     */
    @GET
    @Path("{title}")
    @Produces("application/pdf")
    public Response getFile(@PathParam("title") String title) {
        File file = null;
        FileOutputStream fileOutputStream = null;

        try {
            BugDTO bugDTO = bugManagement.getBugByTitle(title);
            file = new File(FILE_PATH);
            Document document = new Document();
            fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(document, fileOutputStream);
            exportBugPdf.createPdf(bugDTO, document);


            Response.ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition",
                    "attachment; filename=new-android-book.pdf");
            return response.build();
        } catch (FileNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (DocumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


    }


}


