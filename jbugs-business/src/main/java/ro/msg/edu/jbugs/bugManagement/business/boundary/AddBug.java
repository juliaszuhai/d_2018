package ro.msg.edu.jbugs.bugManagement.business.boundary;

import ro.msg.edu.jbugs.bugManagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugManagement.persistence.entity.Status;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/addBug")
public class AddBug {

    @EJB
    private BugManagement bugManagement;

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response registerUser(@FormParam("title") String title,
                                 @FormParam("description") String description,
                                 @FormParam("targetDate") Date targetDate,
                                 @FormParam("fixedVersion") String fixedVersion,
                                 @FormParam("severity") String severity,
                                 @FormParam("assignedTo") String assignedTo
    ){


        return null;
        }
    }

