package ro.msg.edu.jbugs.notificationmanagement.business.exceptions;

import ro.msg.edu.jbugs.usermanagement.business.exceptions.ExceptionCode;

public class BusinessException extends Exception {
	ExceptionCode exceptionCode;

	public BusinessException() {
		this.exceptionCode = ExceptionCode.UNKNOWN_EXCEPTION;
	}

	public BusinessException(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public BusinessException(String message, ExceptionCode exceptionCode) {
		super(message);
		this.setExceptionCode(exceptionCode);
	}

	public BusinessException(String message, Throwable cause, ExceptionCode exceptionCode) {
		super(message, cause);
		this.setExceptionCode(exceptionCode);
	}

	public BusinessException(Throwable cause, ExceptionCode exceptionCode) {
		super(cause);
		this.setExceptionCode(exceptionCode);
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionCode exceptionCode) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setExceptionCode(exceptionCode);
	}

	public ExceptionCode getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

}
