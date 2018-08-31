package ro.msg.edu.jbugs.bugmanagement.business.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.dto.FilterDTO;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.business.service.BugManagement;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;
import ro.msg.edu.jbugs.usermanagement.business.utils.Secured;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

@Path("/list-bugs")
public class BugManagementController {

    @EJB
    private BugManagement bugManagement;

    @GET
    //@Secured("BUG_MANAGEMENT")
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public String getAllBugs() throws JsonProcessingException {
        List<BugDTO> allBugs = bugManagement.getAllBugs();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(allBugs);
    }

    @GET
    //@Secured("BUG_MANAGEMENT")
    @Path("/getByFilter")
    @Produces("application/json")
    public String filter(@QueryParam("title") String title,
                         @QueryParam("description") String description,
                         @QueryParam("status") Status status,
                         @QueryParam("severity") Severity severity,
                         @QueryParam("index") int index,
                         @QueryParam("amount") int amount,
                         @QueryParam("id") Long id) throws JsonProcessingException, BusinessException {
        FilterDTO allBugs = bugManagement.filter(title, description, status, severity, index, amount, id);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(allBugs);

    }


    @POST
    @Secured("BUG_MANAGEMENT")
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/update-bug")
    public Response updateBug(BugDTO bugDTO) {
        try {
            bugManagement.updateBug(bugDTO);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @GET
    @Secured("BUG_MANAGEMENT")
    @Path("/countBugByStatus")
    @Produces("application/json")
    public Response countBugByStatus(@QueryParam("status") Status status) {
        try {
            Long numberOfBug = bugManagement.countBugsByStatus(status);
            return Response.ok(numberOfBug.toString()).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }

    @GET
    @Path("/get-attachment")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getAttachmentsForBug(@QueryParam("bugId") Long bugId) {
        try {
            List<File> files = bugManagement.getAttachmentForBug(bugId);
            for (int i = 0; i < files.size(); i++) {
                Response.ResponseBuilder response = Response.ok(files.get(i), MediaType.APPLICATION_OCTET_STREAM);
                response.header("Content-Disposition",
                        "attachment; filename=" + files.get(i).getName() + "");
                return response.build();
            }
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
        }
    }





    @GET
    @Secured("BUG_MANAGEMENT")
    @Path("/get-status-successors")
    @Produces("application/json")
    public String getStatusSuccessors(@QueryParam("id") Long id) throws BusinessException, JsonProcessingException {

        List<Status> allBugs = bugManagement.getStatusSuccessor(id);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(allBugs);

    }

}
