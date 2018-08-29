package ro.msg.edu.jbugs.bugmanagement.persistence.entity;

public enum Severity {

    CRITICAL(4),
    HIGH(2),
    MEDIUM(1),
    LOW(0);
    private final int value;

    Severity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
