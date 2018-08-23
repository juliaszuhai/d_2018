package ro.msg.edu.jbugs.userManagement.business.boundary;


import com.google.gson.Gson;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/manageusers")
public class ManageUsers {
    @EJB
    private UserManagementController userManagementController;

    @GET
    public Response getAllUsers(){
        try{
            List<UserDTO> allUsers = userManagementController.getAllUsers();
            String allUsersJson = new Gson().toJson(allUsers);
            return Response.ok(allUsersJson).build();
        } catch (Exception e){
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
    }
}
