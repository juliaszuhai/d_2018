package ro.msg.edu.jbugs.notificationmanagement.business.service;

public class NotificationDeleted {
    private static NotificationDeleted ourInstance = new NotificationDeleted();

    private NotificationDeleted() {
    }

    public static NotificationDeleted getInstance() {
        return ourInstance;
    }
}
