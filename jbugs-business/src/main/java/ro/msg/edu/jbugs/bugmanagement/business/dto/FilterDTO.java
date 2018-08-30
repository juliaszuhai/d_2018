package ro.msg.edu.jbugs.bugmanagement.business.dto;

import java.util.List;

public class FilterDTO {


    private List<BugDTO> filteredList;
    private Integer actualListSize;

    public List<BugDTO> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<BugDTO> filteredList) {
        this.filteredList = filteredList;
    }

    public Integer getActualListSize() {
        return actualListSize;
    }

    public void setActualListSize(Integer actualListSize) {
        this.actualListSize = actualListSize;
    }
}
