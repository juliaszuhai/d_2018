package ro.msg.edu.jbugs.userManagement.business.boundary;


import com.google.gson.Gson;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/manageusers")
public class ManageUsers {
    @EJB
    private UserManagementController userManagementController;

    @GET
    @Path("/get-all-users")
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

    @GET
    @Produces("application/json")
    @Path("/update-user")
    public Response updateUser(@QueryParam("username") String username,
                               @QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName,
                               @QueryParam("email") String email,
                               @QueryParam("phoneNumber") String phoneNumber) {
        try {
            User user = userManagementController.getUserForUsername(username);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            userManagementController.updateUser(user);
            return Response.ok().build();
        } catch (BusinessException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode().getMessage()).build();
        }
    }


    @POST
    @Path("/register-user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(final  UserDTO userDTO){

        try {
            userManagementController.createUser(userDTO);

            return Response.status(Response.Status.CREATED).build();
        } catch (BusinessException e){
//            return e.getExceptionCode().toString();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
