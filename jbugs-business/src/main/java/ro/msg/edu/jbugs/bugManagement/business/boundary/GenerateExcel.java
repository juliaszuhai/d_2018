package ro.msg.edu.jbugs.bugManagement.business.boundary;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.*;


@Path("/view-bugs")
public class GenerateExcel {
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
                new TreeMap< String, Object[] >();

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
     *  TODO : O clasa de control, sau in controller existent o metoda care va fi apelata aici
     *
     * @param titles - list of selected titles
     * @return - response
     */
    @GET
    @Secured("BUG_MANAGEMENT")
    @Path("{titles}")
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
            Response.ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition",
                    "attachment; filename=new-excel-file.xlsx");
            file.deleteOnExit();
            return response.build();

        } catch(Exception e){
            e.printStackTrace();
            System.out.println();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


    }


}
