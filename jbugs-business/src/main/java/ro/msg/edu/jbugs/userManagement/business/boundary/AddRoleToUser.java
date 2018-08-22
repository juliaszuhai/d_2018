package ro.msg.edu.jbugs.userManagement.business.boundary;


import ro.msg.edu.jbugs.userManagement.business.control.PermissionManagementController;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.userManagement.business.dto.UserDTOHelper;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.userManagement.persistence.entity.Role;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/addrole")
public class AddRoleToUser {

    @EJB
    private PermissionManagementController permissionManagementController;

    @GET
    public void doSomthing(){}

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response addRoleToUser(@FormParam("username") String username,
                                  @FormParam("roleType") String roleType){
        try {
            permissionManagementController.addRoleToUser(roleType,username);
            return Response.ok("Role added").build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }
}
