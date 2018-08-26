package ro.msg.edu.jbugs.bugmanagement.business.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.bugmanagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.usermanagement.business.utils.Secured;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/listBugs")
public class ListBugs {

    @EJB
    private BugManagement bugManagement;

    @GET
    @Secured("BUG_MANAGEMENT")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String getAllBugs() throws JsonProcessingException {
        List<BugDTO> allBugs = bugManagement.getAllBugs();
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(allBugs);


    }

    @GET
    @Secured("BUG_MANAGEMENT")
    @Path("/getByFilter")
    @Produces("application/json")
    public String filter(@QueryParam("title") String title, @QueryParam("description") String description, @QueryParam("status") Status status, @QueryParam("severity") Severity severity) throws JsonProcessingException, BusinessException {
        List<BugDTO> allBugs = bugManagement.filter(title, description, status, severity);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(allBugs);

    }

    @GET
    @Path("/sort")
    @Produces("application/json")
    public String filter(@QueryParam("title") boolean title, @QueryParam("version") boolean version) throws JsonProcessingException, BusinessException {
        List<BugDTO> allBugs = bugManagement.sort(title, version);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(allBugs);


    }
}
