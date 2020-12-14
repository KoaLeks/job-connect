package at.ac.tuwien.sepm.groupphase.backend.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10000)
    private String message;

    @Column(nullable = false)
    private String type;

    @Column()
    private boolean seen;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Profile recipient;

    @ManyToOne
    private Profile sender;

    @OneToOne
    private Task task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Profile getRecipient() {
        return recipient;
    }

    public void setRecipient(Profile recipient) {
        this.recipient = recipient;
    }

    public Profile getSender() {
        return sender;
    }

    public void setSender(Profile sender) {
        this.sender = sender;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return seen == that.seen &&
            Objects.equals(id, that.id) &&
            Objects.equals(message, that.message) &&
            Objects.equals(type, that.type) &&
            Objects.equals(event, that.event) &&
            Objects.equals(recipient, that.recipient) &&
            Objects.equals(sender, that.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, type, seen, event, recipient, sender);
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + id +
            ", message='" + message + '\'' +
            ", type='" + type + '\'' +
            ", seen=" + seen +
            ", event=" + event +
            ", recipient=" + recipient +
            ", sender=" + sender +
            '}';
    }


}
