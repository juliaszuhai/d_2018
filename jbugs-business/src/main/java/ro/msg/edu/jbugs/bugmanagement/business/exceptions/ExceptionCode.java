package ro.msg.edu.jbugs.bugmanagement.business.exceptions;

public enum ExceptionCode {

    BUG_NOT_EXPORTED(4001, "Bug not be exported"),
    BUG_NOT_EXIST(4002,"Bug selected not exist"),
    DESCRIPTION_TOO_SHORT(4003,"Bug description is too short.Write more"),
    VERSION_NOT_VALID(4004,"Version is not valid"),
    COULD_NOT_CREATE_BUG(4005,"Can't create bug"),
    BUG_VALIDATION_EXCEPTION(4006, "Bug not valid"),
    USER_NOT_EXIST(4007, "User not exist"),
    SOMETHING_WRONG_WITH_ATTACHMENT(4008, "Something wrong with attachment"),
    STATUS_INCORRECT_EXCEPTION(4010, "The status is incorrect"),
    STATUS_INVALID(4009, "Status is invalid"),
    COULD_NOT_EXPORT_ATTACHMENT(4010, "Could not recreate file"),
    ATTACHMENT_DOES_NOT_EXIST(4011, "Attachment does not exist");
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