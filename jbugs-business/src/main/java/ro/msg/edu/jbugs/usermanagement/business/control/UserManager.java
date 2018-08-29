package ro.msg.edu.jbugs.usermanagement.business.control;


import com.google.gson.Gson;
import ro.msg.edu.jbugs.usermanagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.ExceptionCode;
import ro.msg.edu.jbugs.usermanagement.business.service.UserManagementService;
import ro.msg.edu.jbugs.usermanagement.business.utils.Secured;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/manage-users")
public class UserManager {

	@EJB
	private UserManagementService userManagementController;

	@GET
	@Secured("USER_MANAGEMENT")
	@Path("/get-all-users")
	public Response getAllUsers(@Context SecurityContext securityContext) {
		try {
			List<UserDTO> allUsers = userManagementController.getAllUsers();
			String allUsersJson = new Gson().toJson(allUsers);
			return Response.ok(allUsersJson).build();
		} catch (Exception e) {
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
			if (e.getExceptionCode() == ExceptionCode.USER_HAS_ASSIGNED_BUGS) {
				return Response.status(Response.Status.FOUND).entity(e.getExceptionCode().getMessage()).build();
			}
			return Response.status(Response.Status.UNAUTHORIZED).entity(e.getExceptionCode().getMessage()).build();
		}
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/update-user")
	public Response updateUser(UserDTO userDTO) {
		try {
			userManagementController.updateUser(userDTO);
			return Response.ok().build();
		} catch (BusinessException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode().getMessage()).build();
		}
	}


	@POST
	@Path("/register-user")
	@Consumes("application/json")
	@Produces("application/json")
	public Response registerUser(final UserDTO userDTO) {

		try {
			userManagementController.createUser(userDTO);

			return Response.status(Response.Status.CREATED).build();
		} catch (BusinessException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode().getMessage()).build();
		}
	}

	@GET
	@Path("/get-roles-of-user")
	@Produces("application/json")
	@Consumes("application/json")
	public Response getRolesOfUser(@QueryParam("username") String username) {
		try {
			return Response
					.ok(
							new Gson().toJson(userManagementController
									.getUserForUsername(username)
									.getRoles())
					)
					.build();
		} catch (BusinessException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode().getMessage()).build();
		}
	}

	@GET
	@Path("/get-notification-of-user")
	@Produces("application/json")
	@Consumes("application/json")
	public Response getNotificationOfUser(@QueryParam("username") String username) {
		try {
			return Response
					.ok(
							new Gson().toJson(userManagementController
									.getUserForUsername(username)
									.getNotifications())
					)
					.build();
		} catch (BusinessException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getExceptionCode().getMessage()).build();
		}
	}
}
