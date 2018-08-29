package ro.msg.edu.jbugs.bugmanagement.business.exceptions;

public enum ExceptionCode {

    BUG_NOT_EXPORTED(4001, "Bug not be exported"),
    BUG_NOT_EXIST(4002,"Bug selected not exist"),
    DESCRIPTION_TOO_SHORT(4003,"Bug description is too short.Write more"),
    VERSION_NOT_VALID(4004,"Version is not valid"),
    COULD_NOT_CREATE_BUG(4005,"Can't create bug"),
    BUG_VALIDATION_EXCEPTION(4006, "Bug not valid"),
    SOMETHING_WRONG_WITH_ATTACHMENT(4007, "Something wrong with attachment");
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