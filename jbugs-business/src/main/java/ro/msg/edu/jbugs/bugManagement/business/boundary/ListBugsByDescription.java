package ro.msg.edu.jbugs.bugManagement.business.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/listBugsByDescription")
public class ListBugsByDescription {

    @EJB
    private BugManagement bugManagement;

    @GET
    @Path("{description}")
    @Produces("application/json")
//     @Consumes("application/x-www-form-urlencoded")
    public String getBugsByDescription(@PathParam("description") String description) throws JsonProcessingException, BusinessException {
        List<BugDTO> allBugs=bugManagement.getBugsByDescription(description);
        ObjectMapper mapper=new ObjectMapper();

        String jsonResponse=mapper.writeValueAsString(allBugs);

        return jsonResponse;
    }
}
