package ro.msg.edu.jbugs.userManagement.business.boundary;


import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;

import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/update_user")
public class UpdateUser {
    @EJB
    private UserManagementController userManagement;

    @GET
    public String getUsers() {
        return "qwer";
    }

    @POST
    @Consumes("application/json")
    public Response updateUser(String username) {

//        System.out.println(userJson.getString("username"));

        return Response.ok("qwert").build();

    }
}
