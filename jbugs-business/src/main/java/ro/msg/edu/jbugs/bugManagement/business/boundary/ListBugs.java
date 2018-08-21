package ro.msg.edu.jbugs.bugManagement.business.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Bug;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/listBugs")
public class ListBugs {

    @EJB
    private BugManagement bugManagement;

    @GET
   // @Produces("application/json")
   // @Consumes("application/x-www-form-urlencoded")
    public String getAllBugs() throws JsonProcessingException {
        List<BugDTO> allBugs=bugManagement.getAllBugs();
        ObjectMapper mapper=new ObjectMapper();

        String jsonResponse=mapper.writeValueAsString(allBugs);

        return jsonResponse;
    }
}
