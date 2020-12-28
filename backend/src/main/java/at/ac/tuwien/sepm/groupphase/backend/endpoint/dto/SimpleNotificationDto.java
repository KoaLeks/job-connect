package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class SimpleNotificationDto {
    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    private String message;
    @NotNull
    @NotBlank
    private String type;
    private boolean seen;
    @NotNull
    private SimpleEventDto event;
    @NotNull
    private SimpleProfileDto recipient;
    @NotNull
    private SimpleProfileDto sender;
    @NotNull
    private Long taskId;

    private Boolean favorite = false;

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

    public SimpleProfileDto getRecipient() {
        return recipient;
    }

    public void setRecipient(SimpleProfileDto recipient) {
        this.recipient = recipient;
    }

    public SimpleProfileDto getSender() {
        return sender;
    }

    public void setSender(SimpleProfileDto sender) {
        this.sender = sender;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        SimpleNotificationDto that = (SimpleNotificationDto) o;
        return seen == that.seen &&
            Objects.equals(message, that.message) &&
            Objects.equals(type, that.type) &&
            Objects.equals(event, that.event) &&
            Objects.equals(recipient, that.recipient) &&
            Objects.equals(sender, that.sender) &&
            Objects.equals(taskId, that.taskId) &&
            Objects.equals(favorite, that.favorite) &&
            Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, type, seen, event, recipient, sender, taskId, id, favorite);
    }

    @Override
    public String toString() {
        return "SimpleNotificationDto{" +
            "id=" + id +
            ", message='" + message + '\'' +
            ", type='" + type + '\'' +
            ", seen=" + seen +
            ", event=" + event +
            ", recipient=" + recipient +
            ", sender=" + sender +
            ", taskId=" + sender +
            ", favorite=" + favorite +
            '}';
    }
}
