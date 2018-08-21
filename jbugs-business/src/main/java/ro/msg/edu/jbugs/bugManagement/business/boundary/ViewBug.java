package ro.msg.edu.jbugs.bugManagement.business.boundary;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.control.ExportBugPdf;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Path("/viewBug")
public class ViewBug {

    @EJB
    private ExportBugPdf exportBugPdf;

    @EJB
    private BugManagement bugManagement;

    private static final String FILE_PATH = "t:/BugPdf.pdf";

//    @GET
//    @Path("{title}")
//    @Produces("application/pdf")
//    public Response getFile(@PathParam("title") String title) {
//
//        FileOutputStream file = null;
//
//        try {
//
//            BugDTO bugDTO = bugManagement.getBugByTitle(title);
//            file = new FileOutputStream(FILE_PATH);
//
//            Document document = exportBugPdf.createPdf(FILE_PATH,bugDTO);
//            PdfWriter pdfWriter =PdfWriter.getInstance(document, file);
//            Response.ResponseBuilder response = Response.ok((Object) file);
//            response.header("Content-Disposition",
//                    "attachment; filename=new-android-book.pdf");
//            return response.build();
//        } catch (FileNotFoundException e) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        } catch (DocumentException e) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        } catch (BusinessException e) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//
//
//
//    }

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response exportBug(@FormParam("title") String title) {
        try {

            // Authenticate the user using the credentials provided
            BugDTO bugDTO = bugManagement.getBugByTitle(title);

            // Issue a token for the user
            String token = issueToken(bugDTO);
            exportBugPdf.createPdf(bugDTO);
            // Return the token on the response
            return Response.ok("{\"token\": \"" + token + "\"}").build();
        } catch (BusinessException e) {
            System.out.println("-----------------------------\n\n\n EXCEPTION" + e.getExceptionCode() + " \n\n\n ------------");
            System.out.println();
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String issueToken(BugDTO bugDTO) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        Date out = Date.from(tomorrowMidnight.atZone(ZoneId.systemDefault()).toInstant());

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer(bugDTO.getTitle())
                    .withExpiresAt(out)
                    .withClaim("description",bugDTO.getDescription())
                    .withClaim("version", bugDTO.getVersion())
                    .withClaim("targetDate", bugDTO.getTargetDate())

                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
            return "";
        }

    }
}


