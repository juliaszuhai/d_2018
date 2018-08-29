package ro.msg.edu.jbugs.bugmanagement.persistence.entity;

import ro.msg.edu.jbugs.usermanagement.persistence.entity.BaseEntity;

import javax.persistence.*;
import java.sql.Blob;
@Entity
@Table(name = "attachments")
@NamedQueries(
        {
                @NamedQuery(name = Attachment.GET_ALL_ATTACHMENTS, query = "SELECT a FROM Attachment a"),
                @NamedQuery(name = Attachment.GET_ATTACHMENTS_FOR_BUG,query="SELECT a FROM Attachment a INNER JOIN Bug b ON b.id=:id"),
        }
)
public class Attachment extends BaseEntity {

    @Transient
    public final static String GET_ALL_ATTACHMENTS = "get_All_Attachments";
    public final static String GET_ATTACHMENTS_FOR_BUG="get_Attachments_For_Bug";

    @Column(name = "attachment", nullable = true)
    private Blob attachment;


    public Blob getAttachment() {
        return attachment;
    }

    public void setAttachment(Blob attachment) {
        this.attachment = attachment;
    }


}
