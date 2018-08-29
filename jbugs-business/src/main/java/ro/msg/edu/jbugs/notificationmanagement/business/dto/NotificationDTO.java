package ro.msg.edu.jbugs.notificationmanagement.business.dto;

import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.TypeNotification;

import java.util.Date;

public class NotificationDTO {

	private Long id;
	private Long URLBug;
	private String message;
	private Date dateSent;
	private TypeNotification typeNotification;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getURLBug() {
		return URLBug;
	}

	public void setURLBug(Long URLBug) {
		this.URLBug = URLBug;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date targetDate) {
		this.dateSent = targetDate;
	}

	public TypeNotification getTypeNotification() {
		return typeNotification;
	}

	public void setTypeNotification(TypeNotification typeNotification) {
		this.typeNotification = typeNotification;
	}
}


