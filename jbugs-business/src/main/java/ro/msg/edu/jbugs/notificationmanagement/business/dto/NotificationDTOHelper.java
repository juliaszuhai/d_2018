package ro.msg.edu.jbugs.notificationmanagement.business.dto;

import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.Notification;

public class NotificationDTOHelper {

	private NotificationDTOHelper() {
		//hiding the default constructor
	}

	public static NotificationDTO fromEntity(Notification notification) {
		NotificationDTO notificationDTO = new NotificationDTO();

		notificationDTO.setId(notification.getId());
		notificationDTO.setURLBug(notification.getURLBug());
		notificationDTO.setMessage(notification.getMessage());
		notificationDTO.setTargetDate(notification.getTargetDate());
		notificationDTO.setTypeNotification(notification.getTypeNotification());

		return notificationDTO;
	}

	public static Notification toEntity(NotificationDTO notificationDTO) {
		Notification notification = new Notification();

		notification.setMessage(notificationDTO.getMessage());
		notification.setTargetDate(notificationDTO.getTargetDate());
		notification.setURLBug(notificationDTO.getURLBug());
		notification.setTypeNotification(notificationDTO.getTypeNotification());

		return notification;
	}
}
