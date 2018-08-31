package ro.msg.edu.jbugs.notificationmanagement.business.service;


import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTOHelper;
import ro.msg.edu.jbugs.notificationmanagement.persistence.dao.NotificationPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.business.exceptions.BusinessException;
import ro.msg.edu.jbugs.usermanagement.business.service.UserManagementService;
import ro.msg.edu.jbugs.usermanagement.persistence.dao.UserPersistenceManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class NotificationManagementService {

    @EJB
    private NotificationPersistenceManager notificationPersistenceManager;

    @EJB
    private UserPersistenceManager userPersistenceManager;

    @EJB
    private UserManagementService userManagementService;

    public List<NotificationDTO> getAllNotifications() {
        return notificationPersistenceManager.getAllNotifications()
                .stream()
                .map(NotificationDTOHelper::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Delete the specified notification from users
     *
     * @param notificationDTO
     */
    public void deleteNotificationFromUsers(NotificationDTO notificationDTO) {
        userPersistenceManager.getAllUsers().forEach(user ->
        {
            try {
                userManagementService.getUserForUsername(user.getUsername()).getNotifications().remove(NotificationDTOHelper.toEntity(notificationDTO));
            } catch (BusinessException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

    }


}
