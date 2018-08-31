package ro.msg.edu.jbugs.notificationmanagement.business.service;

import ro.msg.edu.jbugs.notificationmanagement.business.dto.NotificationDTO;
import ro.msg.edu.jbugs.notificationmanagement.persistence.dao.NotificationPersistenceManager;
import ro.msg.edu.jbugs.usermanagement.business.service.UserManagementService;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class NotificationDeleted {

    @EJB
    private NotificationManagementService notificationManagementService;

    @EJB
    private UserManagementService userManagementController;
    @EJB
    private NotificationPersistenceManager notificationPersistenceManager;

    /**
     * Activate a programatic timer that deletes the notifications that are older than 30 days
     */
    //@Schedule(second = "*", minute = "*", hour = "24")
    public void deleteNotification() {

        List<NotificationDTO> notificationExpired = getNotificationWithDateExpired();

        if (!notificationExpired.isEmpty()) {
            notificationExpired.forEach(notification ->
            {
                //notificationManagementService.deleteNotificationFromUsers(notification);
                //notificationPersistenceManager.deleteNotificationById(notification.getId());

            });

        }

    }

    private List<NotificationDTO> getNotificationWithDateExpired() {

        return notificationManagementService.getAllNotifications().stream()
                .filter(notification -> {

                    LocalDate currentDate = LocalDate.now();
                    LocalDate localDateNotification = notification.getDateSent().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    Duration duration = Duration.between(currentDate.atStartOfDay(), localDateNotification.atStartOfDay());
                    long differentDay = Math.abs(duration.toDays());
                    return (differentDay > 30L);
                }).collect(Collectors.toList());

    }
}
