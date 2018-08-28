package ro.msg.edu.jbugs.notificationmanagement.business.dto;

import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.TypeNotification;

import java.util.Date;

public class NotificationDTO {

	private Long id;
	private String URLBug;
	private String message;
	private Date targetDate;
	private TypeNotification typeNotification;
	/*private String targetDateString;
	private String typeNotificationString;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getURLBug() {
		return URLBug;
	}

	public void setURLBug(String URLBug) {
		this.URLBug = URLBug;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public TypeNotification getTypeNotification() {
		return typeNotification;
	}

	public void setTypeNotification(TypeNotification typeNotification) {
		this.typeNotification = typeNotification;
	}

	/*public String getTargetDateString() {
		return targetDateString;
	}

	public void setTargetDateString(String targetDateString) {
		this.targetDateString = targetDateString;
	}

	public String getTypeNotificationString() {
		return typeNotificationString;
	}

	public void setTypeNotificationString(String typeNotificationString) {
		this.typeNotificationString = typeNotificationString;
	}*/
}
