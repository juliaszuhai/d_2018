package ro.msg.edu.jbugs.bugmanagement.persistence.entity;

import java.util.ArrayList;
import java.util.List;

public enum Status {

    NEW(1, new ArrayList<Integer>() {{
        add(5);
        add(2);
    }}),
    IN_PROGRESS(2, new ArrayList<Integer>() {{
        add(5);
        add(6);
        add(3);
    }}),
    FIXED(3, new ArrayList<Integer>() {{
        add(1);
        add(4);
    }}),
    CLOSED(4, new ArrayList<>()),
    REJECTED(5, new ArrayList<Integer>() {{
        add(4);
    }}),
    INFO_NEEDED(6, new ArrayList<Integer>() {{
        add(2);
    }});

    private Integer value;
    private List<Integer> succesors;

    Status(Integer value, List<Integer> statuses) {
        this.value = value;
        this.succesors = statuses;

    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<Integer> getSuccesors() {
        return succesors;
    }

    public void setSuccesors(List<Integer> succesors) {
        this.succesors = succesors;
    }
}
