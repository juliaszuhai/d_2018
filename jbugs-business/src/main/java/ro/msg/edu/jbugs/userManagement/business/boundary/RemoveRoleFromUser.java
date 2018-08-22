package ro.msg.edu.jbugs.userManagement.business.boundary;

import ro.msg.edu.jbugs.userManagement.business.control.PermissionManagementController;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/removerole")
public class RemoveRoleFromUser {
    @EJB
    private PermissionManagementController permissionManagementController;

    @GET
    public void nothing(){}

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response revokeRoleFromUser(@FormParam("username") String username,
                                       @FormParam("roleType") String roleType){
        try {
            permissionManagementController.revokeRoleFromUser(roleType,username);
            return Response.ok("Role revoked").build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
