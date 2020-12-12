package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class SimpleNotificationDto {
    @NotNull
    @NotBlank
    private String message;
    @NotNull
    @NotBlank
    private String type;
    private boolean seen;
    @NotNull
    private SimpleEventDto event;

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

    public SimpleEventDto getEvent() {
        return event;
    }

    public void setEvent(SimpleEventDto event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleNotificationDto that = (SimpleNotificationDto) o;
        return seen == that.seen &&
            Objects.equals(message, that.message) &&
            Objects.equals(type, that.type) &&
            Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, type, seen, event);
    }

    @Override
    public String toString() {
        return "SimpleNotificationDto{" +
            "message='" + message + '\'' +
            ", type='" + type + '\'' +
            ", seen=" + seen +
            ", event=" + event +
            '}';
    }
}
