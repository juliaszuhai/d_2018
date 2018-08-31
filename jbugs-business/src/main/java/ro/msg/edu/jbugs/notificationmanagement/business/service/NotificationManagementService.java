package ro.msg.edu.jbugs.notificationmanagement.business.service;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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


    //TODO comentariu javadoc
    public void sendNotification(TypeNotification typeNotification, Object newData, Object oldData, List<User> sendTo) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setTypeNotification(typeNotification);
        notificationDTO.setNewData((new Gson().toJson(newData)));
        if (oldData != null)
            notificationDTO.setOldData((new Gson().toJson(oldData)));

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
