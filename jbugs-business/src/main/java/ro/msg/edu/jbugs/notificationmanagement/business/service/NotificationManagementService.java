package ro.msg.edu.jbugs.notificationmanagement.business.service;

import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTOHelper;
import ro.msg.edu.jbugs.notificationmanagement.persistence.dao.NotificationPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.UserPersistenceManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationManagementService {

	@EJB
	private NotificationPersistenceManager notificationPersistenceManager;

	@EJB
	private UserPersistenceManager userPersistenceManager;



	public List<NotificationDTO> getAllNotifications() {
		return notificationPersistenceManager.getAllNotifications()
				.stream()
				.map(NotificationDTOHelper::fromEntity)
				.collect(Collectors.toList());
	}



}
