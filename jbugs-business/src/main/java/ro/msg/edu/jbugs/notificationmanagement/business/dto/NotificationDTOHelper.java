package ro.msg.edu.jbugs.notificationmanagement.business.dto;

import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.Notification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationDTOHelper {

	private NotificationDTOHelper() {
		//hiding the default constructor
	}

	public static NotificationDTO fromEntity(Notification notification) {
		NotificationDTO notificationDTO = new NotificationDTO();

		notificationDTO.setId(notification.getId());
        notificationDTO.setUrlBug(notification.getUrlBug());
		notificationDTO.setDateSent(notification.getDateSent());
		notificationDTO.setTypeNotification(notification.getTypeNotification());
		notificationDTO.setRead(notification.getRead());
		return notificationDTO;
	}

	public static Notification toEntity(NotificationDTO notificationDTO) throws ParseException {
		Notification notification = new Notification();
		DateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		notification.setId(notificationDTO.getId());
		notification.setDateSent(notificationDTO.getDateSent());
        notification.setUrlBug(notificationDTO.getUrlBug());
		notification.setTypeNotification(notificationDTO.getTypeNotification());
		notification.setTypeNotification(notificationDTO.getTypeNotification());
		notification.setDateSent(formatter.parse(formatter.format(new Date())));
		notification.setUrlBug(notificationDTO.getUrlBug());
		notification.setOldData(notificationDTO.getOldData());
		notification.setNewData(notificationDTO.getNewData());
		notification.setRead(notificationDTO.isRead());

		return notification;
	}
}
