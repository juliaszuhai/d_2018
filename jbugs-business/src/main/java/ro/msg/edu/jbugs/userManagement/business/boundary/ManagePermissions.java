package ro.msg.edu.jbugs.userManagement.business.boundary;


import ro.msg.edu.jbugs.userManagement.business.control.PermissionManagementController;
import ro.msg.edu.jbugs.userManagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.userManagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/managepermissions")
public class ManagePermissions {
    @EJB
    private UserManagementController userManagementController;

    @EJB
    private PermissionManagementController permissionManagementController;


    @GET
    @Produces("application/json")
    @Path("/addpermissiontorole")
    public Response addPermissionToRole(@QueryParam("permissionType") String permissionType,
                                        @QueryParam("roleType") String roleType){

        permissionManagementController.addPermissionToRole(permissionType,roleType);
        return Response.ok("Permission added").build();


    }

    @GET
    @Produces("application/json")
    @Path("revokepermissionfromrole")
    public Response revokePermissionFromRole(@QueryParam("permissionType") String permissionType,
                                             @QueryParam("roleType") String roleType){

        permissionManagementController.revokePermissionFromRole(roleType, permissionType);
        return Response.ok("Permission revoked").build();


    }

    @GET
    @Produces("application/json")
    @Path("addroletouser")
    public Response addRoleToUser(@QueryParam("username") String username,
                                  @QueryParam("roleType") String roleType){
        try {
            permissionManagementController.addRoleToUser(roleType,username);
            return Response.ok("Role added").build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("revokerolefromuser")
    public Response revokeRoleFromUser(@QueryParam("username") String username,
                                       @QueryParam("roleType") String roleType){
        try {
            permissionManagementController.revokeRoleFromUser(roleType,username);
            return Response.ok("Role revoked").build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


}
