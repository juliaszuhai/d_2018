package ro.msg.edu.jbugs.bugManagement.persistence.entity;

import ro.msg.edu.jbugs.userManagement.persistence.entity.BaseEntity;
import ro.msg.edu.jbugs.userManagement.persistence.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "bugs")
@NamedQueries(
        {
                @NamedQuery(name = Bug.GET_ALL_BUGS, query = "SELECT b FROM Bug b"),
                @NamedQuery(name = Bug.GET_BUG_BY_TITLE, query = "SELECT b FROM Bug b WHERE b.title=:title"),
                @NamedQuery(name = Bug.GET_BUG_BY_ID, query = "SELECT b FROM Bug b WHERE b.id=:id"),
                @NamedQuery(name = Bug.GET_BUG_BY_STATUS, query = "SELECT b FROM Bug b WHERE b.status=:status"),
                @NamedQuery(name = Bug.GET_BUG_BY_SEVERITY, query = "SELECT b FROM Bug b WHERE b.severity=:severity"),


        }
)

public class Bug extends BaseEntity<Long>  implements Serializable {


    @Transient
    private final static int MAX_STRING_LENGTH = 40;
    public static final String GET_ALL_BUGS = "get_All_Bugs";
    public static final String GET_BUG_BY_TITLE = "get_Bug_By_Title";
    public static final String GET_BUG_BY_ID="get_Bug_By_Id";
    public static final String GET_BUG_BY_STATUS="get_Bug_By_Status";
    public static final String GET_BUG_BY_SEVERITY="get_Bug_By_Severity";


    @Column(name = "title", length = MAX_STRING_LENGTH, nullable = false)
    private String title;

    @Column(name = "description", length = MAX_STRING_LENGTH, nullable = false)
    private String description;

    @Column(name = "version", length = MAX_STRING_LENGTH, nullable = false)
    private String version;

    @Column(name = "targetDate", nullable = false)
    private Date targetDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "fixedVersion", length = MAX_STRING_LENGTH, nullable = false)
    private String fixedVersion;

    @Column(name = "severity", nullable = false)
    @Enumerated(EnumType.STRING)
    private Severity severity;

    @ManyToOne
    @JoinColumn(name="createdByUser")
    private User createdByUser;

    @ManyToOne
    @JoinColumn(name="assignedTo")
    private User assignedTo;

    public Bug(){

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

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bug bug = (Bug) o;
        return Objects.equals(title, bug.title) &&
                Objects.equals(description, bug.description) &&
                Objects.equals(version, bug.version) &&
                Objects.equals(targetDate, bug.targetDate) &&
                status == bug.status &&
                Objects.equals(fixedVersion, bug.fixedVersion) &&
                severity == bug.severity &&
                Objects.equals(createdByUser, bug.createdByUser) &&
                Objects.equals(assignedTo, bug.assignedTo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), title, description, version, targetDate, status, fixedVersion, severity, createdByUser, assignedTo);
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", targetDate=" + targetDate +
                ", status=" + status +
                ", fixedVersion='" + fixedVersion + '\'' +
                ", severity=" + severity +
                ", createdByUser=" + createdByUser +
                ", assignedTo=" + assignedTo +

                '}';
    }
}
