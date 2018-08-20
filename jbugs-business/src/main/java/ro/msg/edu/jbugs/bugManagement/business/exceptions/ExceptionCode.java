package ro.msg.edu.jbugs.bugManagement.business.exceptions;

public enum ExceptionCode {

    BUG_NOT_EXPORTED(1001, "Bug not be exported"),
    BUG_NOT_EXIST(1002,"Bug selected not exist");
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
