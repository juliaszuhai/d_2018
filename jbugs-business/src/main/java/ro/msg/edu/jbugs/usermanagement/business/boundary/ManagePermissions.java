package ro.msg.edu.jbugs.usermanagement.business.boundary;


import com.google.gson.Gson;
import ro.msg.edu.jbugs.usermanagement.business.control.PermissionManagementController;
import ro.msg.edu.jbugs.usermanagement.business.control.UserManagementController;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/manage-permissions")
public class ManagePermissions {
    @EJB
    private UserManagementController userManagementController;

    @EJB
    private PermissionManagementController permissionManagementController;


    @GET
    @Produces("application/json")
    @Path("/add-permission-to-role")
    public Response addPermissionToRole(@QueryParam("permissionType") String permissionType,
                                        @QueryParam("roleType") String roleType){

        permissionManagementController.addPermissionToRole(permissionType,roleType);
        return Response.ok("Permission added").build();


    }

    @GET
    @Produces("application/json")
    @Path("revoke-permission-from-role")
    public Response revokePermissionFromRole(@QueryParam("permissionType") String permissionType,
                                             @QueryParam("roleType") String roleType){

        permissionManagementController.revokePermissionFromRole(roleType, permissionType);
        return Response.ok("Permission revoked").build();


    }

    @GET
    @Produces("application/json")
    @Path("add-role-to-user")
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
    @Path("revoke-role-from-user")
    public Response revokeRoleFromUser(@QueryParam("username") String username,
                                       @QueryParam("roleType") String roleType){
        try {
            permissionManagementController.revokeRoleFromUser(roleType,username);
            return Response.ok("Role revoked").build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("get-all-permissions")
    @Produces("application/json")
    public Response getAllPermissions() {
        return Response
                .ok(new Gson().toJson(permissionManagementController.getAllPermissions()))
                .build();
    }


    @GET
    @Path("get-all-roles")
    @Produces("application/json")
    public Response getAllRoles() {
        return Response
                .ok(new Gson().toJson(permissionManagementController.getAllRoles()))
                .build();
    }
}
