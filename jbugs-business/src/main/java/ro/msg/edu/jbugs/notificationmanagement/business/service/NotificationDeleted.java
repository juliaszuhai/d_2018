package ro.msg.edu.jbugs.notificationmanagement.business.service;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class NotificationDeleted {

    @EJB
    private NotificationManagementService notificationManagementService;


    /**
     * Activate a programatic timer that deletes the notifications that are older than 30 days
     */
    @Schedule(second = "5", minute = "*", hour = "*")
    public void deleteNotification() {

        notificationManagementService.deleteExpiredNotifications();

    }


}
