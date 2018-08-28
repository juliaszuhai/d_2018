package ro.msg.edu.jbugs.notificationmanagement.business.service;

import ro.msg.edu.jbugs.bugmanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTOHelper;
import ro.msg.edu.jbugs.notificationmanagement.persistence.dao.NotificationPersistenceManager;
import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.Notification;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationManagerService {

	@EJB
	private NotificationPersistenceManager notificationPersistenceManager;


	/**
	 * Create a new notification entity using a Notification DTO.
	 *
	 * @param notificationDTO notification information
	 * @return the DTO of the created entity
	 * @throws BusinessException
	 */
	public NotificationDTO createNotification(NotificationDTO notificationDTO) throws BusinessException {
//		Notification notification = NotificationDTOHelper.toEntity(notificationDTO);
		Notification notification= new Notification();
		notification.setTypeNotification(notificationDTO.getTypeNotification());
		notification.setMessage(notificationDTO.getMessage());
		notification.setTargetDate(notificationDTO.getTargetDate());
		notification.setURLBug(notificationDTO.getURLBug());
		notificationPersistenceManager.createNotification(notification);
		return NotificationDTOHelper.fromEntity(notification);
	}

	public List<NotificationDTO> getAllNotifications() {
		return notificationPersistenceManager.getAllNotifications()
				.stream()
				.map(NotificationDTOHelper::fromEntity)
				.collect(Collectors.toList());
	}


}
