package ro.msg.edu.jbugs.bugmanagement.business.boundary;

import ro.msg.edu.jbugs.bugmanagement.business.control.BugManagement;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.bugmanagement.business.dto.NameIdDTO;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;
import ro.msg.edu.jbugs.usermanagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Path("/add-bug")
public class AddBug {

    @EJB
    private BugManagement bugManagement;

    @EJB
    private UserManagementController userManagement;

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response addBug(final BugDTO bugDTO) {
        try {
            bugManagement.createBug(bugDTO);
            return Response.status(Response.Status.CREATED).build();

        } catch (ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
            }

    }
}

