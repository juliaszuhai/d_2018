package ro.msg.edu.jbugs.bugmanagement.persistence.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Status {

    NEW(1, Arrays.asList(5, 2)),
    IN_PROGRESS(2, Arrays.asList(5, 6, 3)),
    FIXED(3, Arrays.asList(1, 4)),
    CLOSED(4, new ArrayList<>()),
    REJECTED(5, Arrays.asList(4)),
    INFO_NEEDED(6, Arrays.asList(2));

    private Integer value;
    private List<Integer> succesors;

    Status(Integer value, List<Integer> statuses) {
        this.value = value;
        this.succesors = statuses;

    }

    public Integer getValue() {
        return value;
    }


    public List<Integer> getSuccesors() {
        return new ArrayList<>(succesors);
    }


}
