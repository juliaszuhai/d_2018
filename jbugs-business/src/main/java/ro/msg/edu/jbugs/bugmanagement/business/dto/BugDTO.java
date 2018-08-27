package ro.msg.edu.jbugs.bugmanagement.business.dto;

import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Severity;
import ro.msg.edu.jbugs.bugmanagement.persistence.entity.Status;

import java.util.Date;

public class BugDTO {

    private Long id;
    private String title;
    private String description;
    private String version;
    private Date targetDate;
    private Status status;
    private String fixedVersion;
    private Severity severity;
    private NameIdDTO createdByUser;
    private NameIdDTO assignedTo;
    private String targetDateString;
    private String statusString;
    private String severityString;
    private String createdByUserString;
    private String assignedToString;


    public String getCreatedByUserString() {
        return createdByUserString;
    }

    public void setCreatedByUserString(String createdByUserString) {
        this.createdByUserString = createdByUserString;
    }

    public String getAssignedToString() {
        return assignedToString;
    }

    public void setAssignedToString(String assignedToString) {
        this.assignedToString = assignedToString;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getSeverityString() {
        return severityString;
    }

    public void setSeverityString(String severityString) {
        this.severityString = severityString;
    }

    public String getTargetDateString() {
        return targetDateString;
    }

    public void setTargetDateString(String targetDateString) {
        this.targetDateString = targetDateString;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFixedVersion() {
        return fixedVersion;
    }

    public void setFixedVersion(String fixedVersion) {
        this.fixedVersion = fixedVersion;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public NameIdDTO getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(NameIdDTO createdByUser) {
        this.createdByUser = createdByUser;
    }

    public NameIdDTO getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(NameIdDTO assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
