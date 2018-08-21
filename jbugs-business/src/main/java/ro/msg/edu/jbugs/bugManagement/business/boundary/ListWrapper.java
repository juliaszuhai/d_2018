package ro.msg.edu.jbugs.bugManagement.business.boundary;

import java.util.List;

public class ListWrapper {
    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    List<String>  titles;
}
