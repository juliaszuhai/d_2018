package ro.msg.edu.jbugs.bugmanagement.business.dto;

public class AttachmentDTO {

    private Long id;
    private byte[] attachment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }
}
