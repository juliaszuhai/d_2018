package ro.msg.edu.jbugs.userManagement.business.boundary;

import ro.msg.edu.jbugs.userManagement.business.control.PermissionManagementController;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/removepermission")
public class RemovePermissionFromRole {
    @EJB
    private PermissionManagementController permissionManagementController;

    @GET
    public void doSomthing(){}

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response revokePermissionFromRole(@FormParam("permissionType") String permissionType,
                                        @FormParam("roleType") String roleType){

        permissionManagementController.revokePermissionFromRole(roleType, permissionType);
        return Response.ok("Permission revoked").build();


    }
}
