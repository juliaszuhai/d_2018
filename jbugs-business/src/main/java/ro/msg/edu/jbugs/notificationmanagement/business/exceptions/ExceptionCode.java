package ro.msg.edu.jbugs.notificationmanagement.business.exceptions;

/**
 * Provides exception codes and description
 */
public enum ExceptionCode {

	NOTIFICATION_NOT_CREATED(7001, "Notification not created"),
	NOTIFICATION_INEXISTENT(7002, "Notification doesn't exist");

	int id;
	String message;

	ExceptionCode(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

}
