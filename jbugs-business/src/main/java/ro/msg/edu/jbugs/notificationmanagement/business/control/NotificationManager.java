package ro.msg.edu.jbugs.notificationmanagement.business.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.notificationmanagement.business.service.NotificationManagementService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;


@Path("/notifications")
public class NotificationManager {

	@EJB
	private NotificationManagementService notificationManagerService;


	@GET
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public String getAllNotifications() throws JsonProcessingException {
		List<NotificationDTO> allNotifications = notificationManagerService.getAllNotifications();
		ObjectMapper mapper = new ObjectMapper();

		return mapper.writeValueAsString(allNotifications);
	}


}
