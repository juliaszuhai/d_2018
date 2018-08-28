package ro.msg.edu.jbugs.notificationmanagement.business.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.notificationmanagement.business.service.NotificationManagerService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/notifications")
public class NotificationManager {

	@EJB
	private NotificationManagerService notificationManagerService;


	@POST
	@Path("/create-notification")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createNotification(final NotificationDTO notificationDTO) {
		try {
			notificationManagerService.createNotification(notificationDTO);
			return Response.status(Response.Status.CREATED).build();
		} catch (BusinessException e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@GET
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public String getAllNotifications() throws JsonProcessingException {
		List<NotificationDTO> allNotifications = notificationManagerService.getAllNotifications();
		ObjectMapper mapper = new ObjectMapper();

		return mapper.writeValueAsString(allNotifications);
	}
}
