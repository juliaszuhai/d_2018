package ro.msg.edu.jbugs.userManagement.business.boundary;


import com.google.gson.Gson;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.business.utils.Secured;



import javax.ejb.EJB;
import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;

import javax.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/manage-users")
public class ManageUsers {
    @EJB
    private UserManagementController userManagementController;

    @GET
    @Secured("USER_MANAGEMENT")
    @Path("/get-all-users")
    public Response getAllUsers(@Context SecurityContext securityContext){
        try{
            List<UserDTO> allUsers = userManagementController.getAllUsers();
            String allUsersJson = new Gson().toJson(allUsers);
            return Response.ok(allUsersJson).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/activate-user")
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
    @Path("/deactivate-user")
    public Response deactivateUser(@QueryParam("username") String username) {
        try {
            userManagementController.deactivateUser(username);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getExceptionCode().getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update-user")
    public Response updateUser(UserDTO userDTO) {
        try {
            userManagementController.updateUser(userDTO);
            return Response.ok().build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode().getMessage()).build();
        }
    }


    @POST
    @Path("/register-user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(final UserDTO userDTO) {

        try {
            userManagementController.createUser(userDTO);
            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
