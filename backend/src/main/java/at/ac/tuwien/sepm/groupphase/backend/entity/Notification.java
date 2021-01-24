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

    @Column
    private Boolean favorite = false;

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

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
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
            Objects.equals(favorite, that.favorite) &&
            Objects.equals(sender, that.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, type, seen, event, recipient, sender, favorite);
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
            ", favorite=" + favorite +
            '}';
    }

    public static final class NotificationBuilder {
        private Long id;
        private String message;
        private String type;
        private boolean seen;
        private Event event;
        private Profile recipient;
        private Profile sender;
        private Task task;
        private Boolean favorite = false;

        private NotificationBuilder() {
        }

        public static NotificationBuilder aNotification() {
            return new NotificationBuilder();
        }

        public NotificationBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public NotificationBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public NotificationBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public NotificationBuilder withSeen(boolean seen) {
            this.seen = seen;
            return this;
        }

        public NotificationBuilder withEvent(Event event) {
            this.event = event;
            return this;
        }

        public NotificationBuilder withRecipient(Profile recipient) {
            this.recipient = recipient;
            return this;
        }

        public NotificationBuilder withSender(Profile sender) {
            this.sender = sender;
            return this;
        }

        public NotificationBuilder withTask(Task task) {
            this.task = task;
            return this;
        }

        public NotificationBuilder withFavorite(Boolean favorite) {
            this.favorite = favorite;
            return this;
        }

        public Notification build() {
            Notification notification = new Notification();
            notification.setId(id);
            notification.setMessage(message);
            notification.setType(type);
            notification.setSeen(seen);
            notification.setEvent(event);
            notification.setRecipient(recipient);
            notification.setSender(sender);
            notification.setTask(task);
            notification.setFavorite(favorite);
            return notification;
        }
    }
}
