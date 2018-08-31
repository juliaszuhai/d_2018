package ro.msg.edu.jbugs.bugmanagement.business.control;


import com.google.gson.Gson;
import org.glassfish.jersey.media.multipart.FormDataParam;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.bugmanagement.business.service.BugManagement;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Bug;
import ro.msg.edu.jbugs.usermanagement.business.service.UserManagementService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("/bug-management")
public class BugAdditionManager {

    @EJB
    private BugManagement bugManagement;

    @EJB
    private UserManagementService userManagement;

    @POST
    @Path("/add-bug")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addBug(final BugDTO bugDTO) {
        try {
            Bug addedBug = bugManagement.createBug(bugDTO);
            return Response.ok(new Gson().toJson(((Bug) addedBug).getId())).build();
        } catch (ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getExceptionCode().getMessage()).build();
        }

    }


    @POST
    @Path("/add-file")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response addFile(@FormDataParam("id") Long id,
                            @FormDataParam("fileName") String fileName,
                            @FormDataParam("attachment") File attachment) {
        try {
            bugManagement.addFileToBug(attachment, fileName, id);
            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getExceptionCode().getMessage()).build();
        }


    }


}

