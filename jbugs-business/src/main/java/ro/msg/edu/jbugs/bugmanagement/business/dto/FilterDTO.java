package ro.msg.edu.jbugs.bugmanagement.business.dto;

import java.util.List;

public class FilterDTO {


    private List<BugDTO> filteredList;
    private Long actualListSize;

    public List<BugDTO> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<BugDTO> filteredList) {
        this.filteredList = filteredList;
    }

    public Long getActualListSize() {
        return actualListSize;
    }

    public void setActualListSize(Long actualListSize) {
        this.actualListSize = actualListSize;
    }
}
