package ro.msg.edu.jbugs.notificationmanagement.business.service;


import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.msg.edu.jbugs.bugmanagement.business.dto.BugDTO;
import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTOHelper;
import ro.msg.edu.jbugs.notificationmanagement.persistence.dao.NotificationPersistenceManager;
import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.TypeNotification;
import ro.msg.edu.jbugs.usermanagement.business.control.AuthenticationManager;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationManagementService {
    final static Logger log = LogManager.getLogger(AuthenticationManager.class.getName());

    @EJB
    private NotificationPersistenceManager notificationPersistenceManager;


    public List<NotificationDTO> getAllNotifications() {
        return notificationPersistenceManager.getAllNotifications()
                .stream()
                .map(NotificationDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

	/**
	 * Creates a Notification to be added to user's Notification List
	 *
	 * @param typeNotification
	 * @param newData          new User or Bug data which was created or updated
	 * @param oldData          data before update; can be null in case of creation
	 * @param sendTo           list of users to receive the notification
	 */
    public void sendNotification(TypeNotification typeNotification, Object newData, Object oldData, List<User> sendTo) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setRead(false);
        notificationDTO.setTypeNotification(typeNotification);

        notificationDTO.setNewData((new Gson().toJson(newData)));
        if (oldData != null)
            notificationDTO.setOldData((new Gson().toJson(oldData)));
        if (typeNotification.equals(TypeNotification.BUG_CLOSED) | typeNotification.equals(TypeNotification.BUG_CREATED)
                | typeNotification.equals(TypeNotification.BUG_STATUS_UPDATED) | typeNotification.equals(TypeNotification.BUG_UPDATED)) {
            BugDTO bugDto = (BugDTO) newData;

            notificationDTO.setUrlBug(bugDto.getId());
        }

        sendTo.forEach(user -> {
            try {
                user.getNotifications().add(NotificationDTOHelper.toEntity(notificationDTO));
            } catch (ParseException | NullPointerException e) {
                log.catching(e);
            }
        });

    }

    public Integer deleteExpiredNotifications() {
        return notificationPersistenceManager.deleteExpiredNotifications();
    }

}
