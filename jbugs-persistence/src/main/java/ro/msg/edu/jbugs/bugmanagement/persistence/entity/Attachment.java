package ro.msg.edu.jbugs.bugmanagement.persistence.entity;

import ro.msg.edu.jbugs.usermanagement.persistence.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
@Entity
@Table(name = "attachments")
@NamedQueries(
        {
                @NamedQuery(name = Attachment.GET_ALL_ATTACHMENTS, query = "SELECT a FROM Attachment a"),
                @NamedQuery(name = Attachment.GET_ATTACHMENTS_FOR_BUG,query="SELECT a FROM Attachment a INNER JOIN Bug b ON b.id=:id"),
        }
)
public class Attachment extends BaseEntity implements Serializable {

    @Transient
    public static final String GET_ALL_ATTACHMENTS = "get_All_Attachments";
    public static final String GET_ATTACHMENTS_FOR_BUG = "get_Attachments_For_Bug";

    @Column(name = "attachment", nullable = true)
    private Blob attachmentFile;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "extension", nullable = true)
    private String extension;


    public Blob getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(Blob attachmentFile) {
        this.attachmentFile = attachmentFile;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
