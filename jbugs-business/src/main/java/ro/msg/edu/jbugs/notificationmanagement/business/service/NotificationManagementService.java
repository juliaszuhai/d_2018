package ro.msg.edu.jbugs.notificationmanagement.business.service;

import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTOHelper;
import ro.msg.edu.jbugs.notificationmanagement.persistence.dao.NotificationPersistenceManager;
import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.Notification;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.UserPersistenceManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationManagementService {

	@EJB
	private NotificationPersistenceManager notificationPersistenceManager;

	@EJB
	private UserPersistenceManager userPersistenceManager;


	/**
	 * Create a new notification entity using a Notification DTO.
	 *
	 * @param notificationDTO notification information
	 * @return the DTO of the created entity
	 * @throws BusinessException
	 */
	public NotificationDTO createNotification(NotificationDTO notificationDTO) throws ParseException {
		Notification notification = NotificationDTOHelper.toEntity(notificationDTO);

		notification.setTypeNotification(notificationDTO.getTypeNotification());
		notification.setMessage(notificationDTO.getMessage());
		notification.setDateSent(getToday());
		notification.setUrlBug(notificationDTO.getUrlBug());

		return NotificationDTOHelper.fromEntity(notification);
	}

	public List<NotificationDTO> getAllNotifications() {
		return notificationPersistenceManager.getAllNotifications()
				.stream()
				.map(NotificationDTOHelper::fromEntity)
				.collect(Collectors.toList());
	}

	public Date getToday() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

		Date today = new Date();

		return formatter.parse(formatter.format(today));
	}


}
