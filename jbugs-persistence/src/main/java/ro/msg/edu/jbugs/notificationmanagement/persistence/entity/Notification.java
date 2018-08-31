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
				@NamedQuery(name = Notification.GET_NOTIFICATION_BY_ID, query = "SELECT n FROM Notification n WHERE n.id=:id"),
				@NamedQuery(name = Notification.DELETE_NOTIFICATION_BY_ID, query = "DELETE FROM Notification n WHERE n.id=:id"),
				@NamedQuery(name = Notification.DELETE_EXPIRED_NOTIFICATIONS, query = "DELETE FROM Notification n WHERE n.dateSent<:dateExpires")
		}
)
public class Notification extends BaseEntity {

	@Transient
	public static final String GET_ALL_NOTIFICATIONS = "get_All_Notifications";
	public static final String GET_NOTIFICATION_BY_TYPE = "get_Notification_By_Type";
	public static final String GET_NOTIFICATIONS_FOR_USER = "get_Notifications_For_User";
	public static final String GET_NOTIFICATION_BY_ID = "get_Notification_By_Id";
	public static final String DELETE_NOTIFICATION_BY_ID = "delete_Notification_By_Id";
	public static final String DELETE_EXPIRED_NOTIFICATIONS = "delete_expired_notifications";

	@Enumerated(EnumType.STRING)
	private TypeNotification typeNotification;

	@Column(name = "dateSent", nullable = false)
	private Date dateSent;


	@Column(name = "urlBug")
	private Long urlBug;

	@Column(name = "oldData")
	private String oldData;

	@Column(name = "newData")
	private String newData;


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


	public Long getUrlBug() {
		return urlBug;
	}

	public void setUrlBug(Long URLBug) {
		this.urlBug = URLBug;
	}

	public String getOldData() {
		return oldData;
	}

	public void setOldData(String oldData) {
		this.oldData = oldData;
	}

	public String getNewData() {
		return newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Notification that = (Notification) o;
		return typeNotification == that.typeNotification &&
				Objects.equals(dateSent, that.dateSent) &&
				Objects.equals(urlBug, that.urlBug) &&
				Objects.equals(oldData, that.oldData) &&
				Objects.equals(newData, that.newData);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), typeNotification, dateSent, urlBug, oldData, newData);
	}

	@Override
	public String toString() {
		return "Notification{" +
				"typeNotification=" + typeNotification +
				", dateSent=" + dateSent +
				", urlBug=" + urlBug +
				", oldData='" + oldData + '\'' +
				", newData='" + newData + '\'' +
				'}';
	}
}
