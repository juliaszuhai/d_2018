package ro.msg.edu.jbugs.notificationmanagement.business.dto;

import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.Notification;

public class NotificationDTOHelper {

	private NotificationDTOHelper() {
		//hiding the default constructor
	}

	public static NotificationDTO fromEntity(Notification notification) {
		NotificationDTO notificationDTO = new NotificationDTO();

		notificationDTO.setId(notification.getId());
        notificationDTO.setUrlBug(notification.getUrlBug());
		notificationDTO.setMessage(notification.getMessage());
		notificationDTO.setDateSent(notification.getDateSent());
		notificationDTO.setTypeNotification(notification.getTypeNotification());

		return notificationDTO;
	}

	public static Notification toEntity(NotificationDTO notificationDTO) {
		Notification notification = new Notification();

		notification.setMessage(notificationDTO.getMessage());
		notification.setDateSent(notificationDTO.getDateSent());
        notification.setUrlBug(notificationDTO.getUrlBug());
		notification.setTypeNotification(notificationDTO.getTypeNotification());

		return notification;
	}
}
