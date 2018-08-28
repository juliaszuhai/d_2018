package ro.msg.edu.jbugs.bugmanagement.business.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.business.service.BugManagement;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;
import ro.msg.edu.jbugs.usermanagement.business.utils.Secured;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/listBugs")
public class BugManagementController {

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
    public String sort(@QueryParam("title") boolean title, @QueryParam("version") boolean version) throws JsonProcessingException, BusinessException {
        List<BugDTO> allBugs = bugManagement.sort(title, version);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(allBugs);


    }


    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/update-bug")
    public Response updateBug(BugDTO bugDTO) {
        try {
            bugManagement.updateBug(bugDTO);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode().getMessage()).build();
        }
    }
}
