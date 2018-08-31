package ro.msg.edu.jbugs.bugmanagement.persistence.entity;

import ro.msg.edu.jbugs.usermanagement.persistence.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "attachments")
@NamedQueries(
        {
                @NamedQuery(name = Attachment.GET_ALL_ATTACHMENTS, query = "SELECT a FROM Attachment a"),
                @NamedQuery(name = Attachment.GET_ATTACHMENTS_FOR_BUG, query = "SELECT a FROM Attachment a INNER JOIN Bug b WHERE b.id=:bugId"),
                @NamedQuery(name = Attachment.GET_ATTACHMENT_FOR_NAME, query = "SELECT a FROM Attachment a WHERE a.name=:name"),
        }
)
public class Attachment extends BaseEntity implements Serializable {

    @Transient
    public static final String GET_ALL_ATTACHMENTS = "get_All_Attachments";
    public static final String GET_ATTACHMENTS_FOR_BUG = "get_Attachments_For_Bug";
    public static final String GET_ATTACHMENT_FOR_NAME = "get_Attachment_For_Name";

    @Column(name = "attachment", nullable = true)
    private byte[] attachmentFile;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "extension", nullable = true)
    private String extension;


    public byte[] getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(byte[] attachmentFile) {
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
