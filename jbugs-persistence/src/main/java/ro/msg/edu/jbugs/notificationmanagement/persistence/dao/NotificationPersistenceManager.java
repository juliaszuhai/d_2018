package ro.msg.edu.jbugs.notificationmanagement.persistence.dao;

import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.Notification;
import ro.msg.edu.jbugs.usermanagement.persistence.entity.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Stateless
public class NotificationPersistenceManager {

    private static final long serialVersionUID = 1L;
    public static final int DAYS_UNTIL_NOTIFICATION_DELETE = 30;

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager em;

    /**
     * Get a list of all notifications for a specific user.
     *
     * @param id
     * @return : List of Notification, empty if there are none
     */
    public List<Notification> getAllNotificationsByUserId(@NotNull Long id) {
        return em.createNamedQuery(User.GET_NOTIFICATIONS_BY_USERID, Notification.class)
                .setParameter("id", id).getResultList();

    }

    /**
     * Get a list of all notifications stored in database.
     *
     * @return List of Notifications, empty if there are non
     */
    public List<Notification> getAllNotifications() {
        TypedQuery<Notification> q = em.createNamedQuery(Notification.GET_ALL_NOTIFICATIONS, Notification.class);
        return q.getResultList();
    }

    public Optional<Notification> getNotificationById(@NotNull Long id) {
        TypedQuery<Notification> q = em.createNamedQuery(Notification.GET_NOTIFICATION_BY_ID, Notification.class)
                .setParameter("id", id);
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Notification createNotification(@NotNull Notification notification) {
        em.persist(notification);
        em.flush();
        return notification;

    }

    /**
     * Delete the notification by id
     *
     * @param id: of the notification
     * @return boolean
     */
    public Boolean deleteNotificationById(@NotNull Long id) {
        TypedQuery<Notification> namedQuery = em.createNamedQuery(Notification.DELETE_NOTIFICATION_BY_ID, Notification.class);
        int idDeletedNotification = namedQuery
                .setParameter("id", id)
                .executeUpdate();
        em.flush();
        return idDeletedNotification > 0;
    }

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
