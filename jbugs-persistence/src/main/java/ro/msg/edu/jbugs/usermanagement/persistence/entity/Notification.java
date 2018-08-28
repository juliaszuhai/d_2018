package ro.msg.edu.jbugs.usermanagement.persistence.entity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.DATE;

@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private TypeNotification typeNotification;

    @Temporal(DATE)
    private Date targetDate;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "URLBug")
    private String URLBug;

    public TypeNotification getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(TypeNotification typeNotification) {
        this.typeNotification = typeNotification;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getURLBug() {
        return URLBug;
    }

    public void setURLBug(String URLBug) {
        this.URLBug = URLBug;
    }
}
