package ro.msg.edu.jbugs.bugManagement.business.boundary;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Path("/view-bugs")
public class GenerateExcel {
    private static final String FILE_PATH ="T:/Try.xlsx";

    @EJB
    private BugManagement bugManagement;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response generate(ListWrapper titles) {
        try {

            for(int i=0;i<titles.getTitles().size();i++)
                {System.out.println(titles.getTitles().get(i));}
            // Authenticate the user using the credentials provided
            List<BugDTO> bugs= bugManagement.getAllBugs();

            List<BugDTO> selectedBugs=new ArrayList<BugDTO>();

            for(int k=0;k<titles.getTitles().size();k++)
            {
                for(int l=0;l<bugs.size();l++)
                {
                    if(titles.getTitles().get(k).equals(bugs.get(l).getTitle()))
                    {
                        selectedBugs.add(bugs.get(l));
                    }
                }
            }
            for(int i=0;i<selectedBugs.size();i++)
            {
                System.out.println(selectedBugs.get(i).getDescription());
            }
            FileInputStream fis = null;
            fis = new FileInputStream(FILE_PATH);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet spreadsheet = workbook.createSheet(" Selected Bugs ");
            Row row;
            Map< String, Object[] > empinfo =
                    new TreeMap< String, Object[] >();

            empinfo.put( "1", new Object[] { "Title", "Description", "Version" ,"TargetDate","Status","FixedVersion","Severity","CreatedBy","AssignedTo"});
            for(int i=0;i<selectedBugs.size();i++)
            {

                int j=i+2;
                empinfo.put(String.valueOf(j) , new Object[] { selectedBugs.get(i).getTitle(), selectedBugs.get(i).getDescription(),selectedBugs.get(i).getVersion() ,selectedBugs.get(i).getTargetDate().toString()
                        , selectedBugs.get(i).getStatus(), selectedBugs.get(i).getFixedVersion(), selectedBugs.get(i).getSeverity(), selectedBugs.get(i).getCreatedByUser().getUsername(), selectedBugs.get(i).getAssignedTo().getUsername()});

                System.out.println(selectedBugs.get(i).toString());
            }
            System.out.println("intra3");
            Set< String > keyid = empinfo.keySet();
            int rowid = 0;

            for (String key : keyid) {
                row = spreadsheet.createRow(rowid++);
                Object [] objectArr = empinfo.get(key);
                int cellid = 0;

                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    cell.setCellValue(obj.toString());
                }
            }
            //Write the workbook in file system
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

            System.out.println("Writesheet.xlsx written successfully");
            // Issue a token for the user
            List<String> token = issueToken(selectedBugs);

            // Return the token on the response
            return Response.ok("{\"token\": \""+token+"\"}").build();
        } catch(Exception e){
            System.out.println("-----------------------------\n\n\n EXCEPTION"+e.getMessage()+" \n\n\n ------------");
            System.out.println();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    private List<String> issueToken(List<BugDTO> bugs) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        Date out = Date.from(tomorrowMidnight.atZone(ZoneId.systemDefault()).toInstant());

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            List<String> tokens= new ArrayList<String>();
            for(int i=0;i<bugs.size();i++)
            {
                String token = JWT.create()
                        .withIssuer(bugs.get(i).getTitle())
                        .withExpiresAt(out)
                        .withClaim("description", bugs.get(i).getDescription())
                        .withClaim("version", bugs.get(i).getVersion())
                        .sign(algorithm);
                tokens.add(token);
            }
            return tokens;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
            return null;
        }

    }
}

