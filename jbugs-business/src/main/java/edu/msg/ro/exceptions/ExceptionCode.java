package edu.msg.ro.exceptions;

public enum ExceptionCode {
    USER_VALIDATION_EXCEPTION(1,"Validation Exception"),
    EMAIL_EXISTS_ALREADY(2,"Email already exists Exception"),
    PASSWORD_NOT_VALID(3,"Password not valid."),
    USERNAME_NOT_VALID(4,"Username not valid");
    int id;
    String message;

    ExceptionCode(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
