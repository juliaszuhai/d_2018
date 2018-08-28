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

	@Column(name="targetDate")
	@Temporal(TemporalType.DATE)
	private Date targetDate;

	@Column(name = "message", nullable = false)
	private String message;

	@Column(name = "URLBug", nullable = false)
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Notification that = (Notification) o;
		return typeNotification == that.typeNotification &&
				Objects.equals(targetDate, that.targetDate) &&
				Objects.equals(message, that.message) &&
				Objects.equals(URLBug, that.URLBug);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), typeNotification, targetDate, message, URLBug);
	}

	@Override
	public String toString() {
		return "Notification{" +
				"typeNotification=" + typeNotification +
				", targetDate=" + targetDate +
				", message='" + message + '\'' +
				", URLBug='" + URLBug + '\'' +
				", id=" + id +
				'}';
	}
}
