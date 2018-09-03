package ro.msg.edu.jbugs.usermanagement.business.control;


import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.usermanagement.business.service.PermissionManagementService;
import ro.msg.edu.jbugs.usermanagement.business.service.UserManagementService;
import ro.msg.edu.jbugs.usermanagement.business.utils.Secured;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/manage-permissions")
public class PermissionManagementControlelr {
	@EJB
	private UserManagementService userManagementController;

	@EJB
	private PermissionManagementService permissionManagementController;


	/**
	 * Adds permission to role.
	 *
	 * @param permissionType
	 * @param roleType
	 * @return
	 */
	@POST
	@Secured("PERMISSION_MANAGEMENT")
	@Produces("application/json")
	@Path("/add-permission-to-role")
	public Response addPermissionToRole(@FormParam("permissionType") String permissionType,
										@FormParam("roleType") String roleType) {
		permissionManagementController.addPermissionToRole(permissionType, roleType);
		return Response.ok().build();
	}

	/**
	 * Revokes a permission from a role.
	 * @param permissionType
	 * @param roleType
	 * @return
	 */
	@POST
	@Secured("PERMISSION_MANAGEMENT")
	@Produces("application/json")
	@Path("/revoke-permission-from-role")
	public Response revokePermissionFromRole(@FormParam("permissionType") String permissionType,
											 @FormParam("roleType") String roleType) {

		permissionManagementController.revokePermissionFromRole(roleType, permissionType);
		return Response.ok().build();


	}

	private String getRequester(@Context HttpHeaders headers) {
		String authorizationHeader = headers.getRequestHeader("authorization").get(0);
		String token = authorizationHeader.substring("Bearer".length()).trim();
		return JWT.decode(token).getClaim("username").asString();

	}

	/**
	 * Adds a role to a user.
	 * @param username
	 * @param roleType
	 * @param headers
	 * @return
	 */
	@POST
	@Secured("PERMISSION_MANAGEMENT")
	@Consumes("application/x-www-form-urlencoded")
	@Path("/add-role-to-user")
	public Response addRoleToUser(@FormParam("username") String username,
								  @FormParam("roleType") String roleType,
								  @Context HttpHeaders headers) {
		try {
			permissionManagementController.addRoleToUser(roleType, username, getRequester(headers));
			return Response.ok().build();
		} catch (BusinessException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
		}
	}

	/**
	 * Revokes a role from a user.
	 * @param username
	 * @param roleType
	 * @param headers
	 * @return
	 */
	@POST
	@Secured("PERMISSION_MANAGEMENT")
	@Produces("application/json")
	@Path("/revoke-role-from-user")
	public Response revokeRoleFromUser(@FormParam("username") String username,
									   @FormParam("roleType") String roleType,
									   @Context HttpHeaders headers) {
		try {
			permissionManagementController.revokeRoleFromUser(roleType, username, getRequester(headers));
			return Response.ok().build();
		} catch (BusinessException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
		}
	}


	/**
	 * Returns all permissions.
	 * @return
	 */
	@GET
	@Secured("PERMISSION_MANAGEMENT")
	@Path("/get-all-permissions")
	@Produces("application/json")
	public Response getAllPermissions() {
		return Response
				.ok(new Gson().toJson(permissionManagementController.getAllPermissions()))
				.build();
	}


	/**
	 * Returns all roles.
	 * @return
	 */
	@GET
	@Secured("PERMISSION_MANAGEMENT")
	@Path("/get-all-roles")
	@Produces("application/json")
	public Response getAllRoles() {
		return Response
				.ok(new Gson().toJson(permissionManagementController.getAllRoles()))
				.build();
	}

	/**
	 * Returns a permission for a role.
	 * @param roleType
	 * @return
	 */
	@GET
	@Secured("PERMISSION_MANAGEMENT")
	@Path("/get-permissions-for-role")
	@Produces("application/json")
	@Consumes("application/json")
	public Response getPermissionsForRole(@QueryParam("roleType") String roleType) {
		try {
			return Response
					.ok(new Gson().toJson(
							permissionManagementController.getPermissionsByRole(roleType)))
					.build();
		} catch (BusinessException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode()).build();
		}
	}
}
