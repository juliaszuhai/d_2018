package ro.msg.edu.jbugs.userManagement.business.boundary;

import ro.msg.edu.jbugs.userManagement.business.control.PermissionManagementController;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/addpermission")
public class AddPermissionToRole {
    @EJB
    private PermissionManagementController permissionManagementController;

    @GET
    public void doSomthing(){}

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response addPermissionToRole(@FormParam("permissionType") String permissionType,
                                        @FormParam("roleType") String roleType){

            permissionManagementController.addPermissionToRole(permissionType,roleType);
            return Response.ok("Permission added").build();


    }
}
