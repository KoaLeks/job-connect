package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.util.Objects;

public class ContactMessage {
    private Long to;
    private String subject;
    private String message;

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactMessage)) return false;
        ContactMessage that = (ContactMessage) o;
        return Objects.equals(to, that.to) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, subject, message);
    }

    @Override
    public String toString() {
        return "ContactMessage{" +
            "to=" + to +
            ", subject='" + subject + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
