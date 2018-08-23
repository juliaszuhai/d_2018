package ro.msg.edu.jbugs.bugManagement.business.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Status;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/listBugs")
public class ListBugs {

    @EJB
    private BugManagement bugManagement;

    @GET
    public String getAllBugs() throws JsonProcessingException {
        List<BugDTO> allBugs=bugManagement.getAllBugs();
        ObjectMapper mapper=new ObjectMapper();

        String jsonResponse=mapper.writeValueAsString(allBugs);

        return jsonResponse;
    }

    @GET
    @Path("/getByFilter")
    @Produces("application/json")
//     @Consumes("application/x-www-form-urlencoded")
    public String filter(@QueryParam("title") String title, @QueryParam("description") String description, @QueryParam("status") Status status, @QueryParam("severity") Severity severity) throws JsonProcessingException, BusinessException {
        List<BugDTO> allBugs=bugManagement.filter(title,description,status,severity);
        ObjectMapper mapper=new ObjectMapper();

        String jsonResponse=mapper.writeValueAsString(allBugs);

        return jsonResponse;
    }
}
