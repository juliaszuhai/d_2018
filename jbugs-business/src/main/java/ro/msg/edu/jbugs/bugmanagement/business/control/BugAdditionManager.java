package ro.msg.edu.jbugs.bugmanagement.business.control;

import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.service.BugManagement;
import ro.msg.edu.jbugs.usermanagement.business.service.UserManagementService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("/add-bug")
public class BugAdditionManager {

    @EJB
    private BugManagement bugManagement;

    @EJB
    private UserManagementService userManagement;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addBug(final BugDTO bugDTO) {
        try {
            bugManagement.createBug(bugDTO);
            return Response.status(Response.Status.CREATED).build();

        } catch (ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getExceptionCode().getMessage()).build();
            }

    }


    /*@POST
    @Path("/with-attachment")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addBugWithAttachment(final BugDTO bugDTO) {
        try {
            bugManagement.createBugWithAttachment();
            return Response.status(Response.Status.CREATED).build();

        } catch (ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }*/
}

