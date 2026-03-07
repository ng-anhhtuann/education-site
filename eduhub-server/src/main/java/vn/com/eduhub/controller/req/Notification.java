package vn.com.eduhub.controller.req;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;

@Generated("jsonschema2pojo")
public class Notification {

    @SerializedName("attachment")
    @Expose
    @Valid
    private List<Attachment> attachment;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("createdAt")
    @Expose
    @DecimalMin("9223372036854775807")
    private Long createdAt;
    @SerializedName("html")
    @Expose
    private String html;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner_id")
    @Expose
    private String ownerId;
    @SerializedName("seen")
    @Expose
    private Boolean seen;
    @SerializedName("title")
    @Expose
    private String title;

    public List<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
    }

    public Notification withAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Notification withContent(String content) {
        this.content = content;
        return this;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Notification withCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Notification withHtml(String html) {
        this.html = html;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Notification withId(String id) {
        this.id = id;
        return this;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Notification withOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Notification withSeen(Boolean seen) {
        this.seen = seen;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Notification withTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Notification.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("attachment");
        sb.append('=');
        sb.append(((this.attachment == null) ? "<null>" : this.attachment));
        sb.append(',');
        sb.append("content");
        sb.append('=');
        sb.append(((this.content == null) ? "<null>" : this.content));
        sb.append(',');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null) ? "<null>" : this.createdAt));
        sb.append(',');
        sb.append("html");
        sb.append('=');
        sb.append(((this.html == null) ? "<null>" : this.html));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("ownerId");
        sb.append('=');
        sb.append(((this.ownerId == null) ? "<null>" : this.ownerId));
        sb.append(',');
        sb.append("seen");
        sb.append('=');
        sb.append(((this.seen == null) ? "<null>" : this.seen));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null) ? "<null>" : this.title));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.createdAt == null) ? 0 : this.createdAt.hashCode()));
        result = ((result * 31) + ((this.attachment == null) ? 0 : this.attachment.hashCode()));
        result = ((result * 31) + ((this.html == null) ? 0 : this.html.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.ownerId == null) ? 0 : this.ownerId.hashCode()));
        result = ((result * 31) + ((this.title == null) ? 0 : this.title.hashCode()));
        result = ((result * 31) + ((this.content == null) ? 0 : this.content.hashCode()));
        result = ((result * 31) + ((this.seen == null) ? 0 : this.seen.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Notification) == false) {
            return false;
        }
        Notification rhs = ((Notification) other);
        return (((((((((this.createdAt == rhs.createdAt) || ((this.createdAt != null) && this.createdAt.equals(rhs.createdAt))) && ((this.attachment == rhs.attachment) || ((this.attachment != null) && this.attachment.equals(rhs.attachment)))) && ((this.html == rhs.html) || ((this.html != null) && this.html.equals(rhs.html)))) && ((this.id == rhs.id) || ((this.id != null) && this.id.equals(rhs.id)))) && ((this.ownerId == rhs.ownerId) || ((this.ownerId != null) && this.ownerId.equals(rhs.ownerId)))) && ((this.title == rhs.title) || ((this.title != null) && this.title.equals(rhs.title)))) && ((this.content == rhs.content) || ((this.content != null) && this.content.equals(rhs.content)))) && ((this.seen == rhs.seen) || ((this.seen != null) && this.seen.equals(rhs.seen))));
    }

}