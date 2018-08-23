package ro.msg.edu.jbugs.userManagement.business.boundary;


import com.google.gson.Gson;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/manageusers")
public class ManageUsers {
    @EJB
    private UserManagementController userManagementController;

    @GET
    @Path("/getallusers")
    public Response getAllUsers(){
        try{
            List<UserDTO> allUsers = userManagementController.getAllUsers();
            String allUsersJson = new Gson().toJson(allUsers);
            return Response.ok(allUsersJson).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/activateuser")
    public Response activateUser(@QueryParam("username") String username) {
        try {
            userManagementController.activateUser(username);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getExceptionCode().getMessage()).build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/deactivateuser")
    public Response deactivateUser(@QueryParam("username") String username) {
        try {
            userManagementController.deactivateUser(username);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getExceptionCode().getMessage()).build();
        }
    }


}
