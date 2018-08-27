package ro.msg.edu.jbugs.bugmanagement.business.control;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.service.BugManagement;
import ro.msg.edu.jbugs.usermanagement.business.utils.Secured;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


@Path("/view-bugs")
public class ExcelGeneratorController {
    private static final String FILE_PATH = "T:/Try.xlsx";

    @EJB
    private BugManagement bugManagement;

    /**
     * The method prepares the data to be written in the Excel file.
     * @param titles - list of selected titles
     * @return a map containing the bugs corresponding to the selected titles
     */
    private Map<String, Object[] > putInMap(List<Long> titles)
    {
        List<BugDTO> selectedBugs=bugManagement.getBugsWithId(titles);
        Map< String, Object[] > empinfo =
                new TreeMap< >();

        empinfo.put( "1", new Object[] { "Title", "Description", "Version" ,"TargetDate","Status","FixedVersion","Severity","CreatedBy","AssignedTo"});
        for(int i=0;i<selectedBugs.size();i++)
        { int j=i+2;
            empinfo.put(Integer.toString(j) , new Object[] { selectedBugs.get(i).getTitle(), selectedBugs.get(i).getDescription(),selectedBugs.get(i).getVersion() ,selectedBugs.get(i).getTargetDate().toString()
                    , selectedBugs.get(i).getStatus(), selectedBugs.get(i).getFixedVersion(), selectedBugs.get(i).getSeverity(), selectedBugs.get(i).getCreatedByUser().getUsername(), selectedBugs.get(i).getAssignedTo().getUsername()});
        }
        return empinfo;
    }

    /**
     * The method generates the Excel file and lets you download it.
     *  TODO : O clasa de service, sau in controller existent o metoda care va fi apelata aici
     *
     * @param titles - list of selected titles
     * @return - response
     */
    @GET
    @Secured("BUG_MANAGEMENT")
    @Produces("application/vnd.ms-excel")
    public Response generate(@QueryParam("titles") List<Long> titles) {
        try {
            File file = new File(FILE_PATH);
            FileInputStream fis = null;
            fis = new FileInputStream(FILE_PATH);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Row row;
            Map< String, Object[] > empinfo=this.putInMap(titles);

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
            Response.ResponseBuilder response = Response.ok(file);
            response.header("Content-Disposition",
                    "attachment; filename=new-excel-file.xlsx");
            file.deleteOnExit();
            return response.build();

        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }


    }
}
