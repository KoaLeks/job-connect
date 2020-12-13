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
    private Profile profile;

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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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
            Objects.equals(profile, that.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, type, seen, event, profile);
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + id +
            ", message='" + message + '\'' +
            ", type='" + type + '\'' +
            ", seen=" + seen +
            ", event=" + event +
            ", profile=" + profile +
            '}';
    }


}
