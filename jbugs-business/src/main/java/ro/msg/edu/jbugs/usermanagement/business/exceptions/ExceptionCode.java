package ro.msg.edu.jbugs.usermanagement.business.exceptions;

/**
 * Provides exception codes and description.
 */
public enum ExceptionCode {
    UNKNOWN_EXCEPTION(1000, "Validation Exception"),
    USER_VALIDATION_EXCEPTION(1001, "Validation Exception"),
    EMAIL_EXISTS_ALREADY(1001, "Email already exists Exception"),
    PASSWORD_NOT_VALID(1002, "Password not valid."),
    USERNAME_NOT_VALID(1003, "Username not valid"),
    USER_DEACTIVATED(1004, "User deactivated"),
    TOKEN_EXPIRED(1005, "Token expired"),
    USER_PERMISSION_VALIDATION(2001, "User does not have this permission"),
    ROLE_DOESNT_EXIST(2002, "User does not have this permission"),
    USER_HAS_ASSIGNED_BUGS(3002, "User still has assigned bugs.");
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
