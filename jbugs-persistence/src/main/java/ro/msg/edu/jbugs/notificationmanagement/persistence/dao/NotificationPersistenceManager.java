package ro.msg.edu.jbugs.notificationmanagement.persistence.dao;

import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.Notification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.*;

@Stateless
public class NotificationPersistenceManager {

    private static final long serialVersionUID = 1L;
    public static final int DAYS_UNTIL_NOTIFICATION_DELETE = 30;

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;



    /**
     * Get a list of all notifications stored in database.
     *
     * @return List of Notifications, empty if there are non
     */
    public List<Notification> getAllNotifications() {
        TypedQuery<Notification> q = em.createNamedQuery(Notification.GET_ALL_NOTIFICATIONS, Notification.class);
        return q.getResultList();
    }


    /**
     * Executes a query which deletes all Notifications older than 30 days
     * @return number of deleted entries
     */
    public Integer deleteExpiredNotifications() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC+2"));//Munich time
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -DAYS_UNTIL_NOTIFICATION_DELETE);//substract the number of days to look back
        Date dateToLookBackAfter = calendar.getTime();
        TypedQuery<Notification> dateExpires = em.createNamedQuery(Notification.DELETE_EXPIRED_NOTIFICATIONS, Notification.class)
                .setParameter("dateExpires", dateToLookBackAfter, TemporalType.DATE);

        return dateExpires.executeUpdate();
    }

}
