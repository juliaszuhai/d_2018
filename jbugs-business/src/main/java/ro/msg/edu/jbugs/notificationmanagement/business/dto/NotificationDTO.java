package ro.msg.edu.jbugs.notificationmanagement.business.dto;

import ro.msg.edu.jbugs.notificationmanagement.persistence.entity.TypeNotification;

import java.util.Date;

public class NotificationDTO {

	private Long id;
	private Long urlBug;
	private Date dateSent;
	private TypeNotification typeNotification;
	private String oldData;
	private String newData;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUrlBug() {
		return urlBug;
	}

	public void setUrlBug(Long urlBug) {
		this.urlBug = urlBug;
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
}


