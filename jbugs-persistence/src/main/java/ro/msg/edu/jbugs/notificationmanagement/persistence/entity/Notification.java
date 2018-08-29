package ro.msg.edu.jbugs.notificationmanagement.persistence.entity;

import ro.msg.edu.jbugs.usermanagement.persistence.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "notification")
@NamedQueries(
		{
				@NamedQuery(name = Notification.GET_ALL_NOTIFICATIONS, query = "SELECT n FROM Notification n"),
				@NamedQuery(name = Notification.GET_NOTIFICATION_BY_TYPE, query = "SELECT n FROM Notification n WHERE n.typeNotification=:type"),
				@NamedQuery(name = Notification.GET_NOTIFICATION_BY_ID, query = "SELECT n FROM Notification n WHERE n.id=:id")
		}
)
public class Notification extends BaseEntity {

	@Transient
	public static final String GET_ALL_NOTIFICATIONS = "get_All_Notifications";
	public static final String GET_NOTIFICATION_BY_TYPE = "get_Notification_By_Type";
	public static final String GET_NOTIFICATIONS_FOR_USER = "get_Notifications_For_User";
	public static final String GET_NOTIFICATION_BY_ID = "get_Notification_By_Id";

	@Enumerated(EnumType.STRING)
	private TypeNotification typeNotification;

	@Column(name = "dateSent", nullable = false)
	private Date dateSent;

	@Column(name = "message", nullable = false)
	private Long message;

	@Column(name = "URLBug")
	private Long URLBug;

	public TypeNotification getTypeNotification() {
		return typeNotification;
	}

	public void setTypeNotification(TypeNotification typeNotification) {
		this.typeNotification = typeNotification;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public Long getMessage() {
		return message;
	}

	public void setMessage(Long message) {
		this.message = message;
	}

	public Long getURLBug() {
		return URLBug;
	}

	public void setURLBug(Long URLBug) {
		this.URLBug = URLBug;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Notification that = (Notification) o;
		return typeNotification == that.typeNotification &&
				Objects.equals(dateSent, that.dateSent) &&
				Objects.equals(message, that.message) &&
				Objects.equals(URLBug, that.URLBug);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), typeNotification, dateSent, message, URLBug);
	}

	@Override
	public String toString() {
		return "Notification{" +
				"typeNotification=" + typeNotification +
				", targetDate=" + dateSent +
				", message='" + message + '\'' +
				", URLBug='" + URLBug + '\'' +
				", id=" + id +
				'}';
	}
}
