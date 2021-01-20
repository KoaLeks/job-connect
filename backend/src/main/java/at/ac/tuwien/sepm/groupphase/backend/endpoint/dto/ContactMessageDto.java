package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class ContactMessageDto {
    @NotNull(message = "Empfänger darf nicht leer sein")
    private Long to;
    @NotNull(message = "Betreff darf nicht leer sein")
    @NotBlank(message = "Betreff darf nicht leer sein")
    @Pattern(regexp = "[a-zA-ZÖöÜüÄä]+([ ]|[a-zA-ZÖöÜüÄä]|[0-9]|[.]|[,]|[(]|[)]|[-]|[/]|[^'\\u0027])*", message = "Pattern muss eingehalten werden")
    private String subject;
    @NotNull(message = "Nachricht darf nicht leer sein")
    @NotBlank(message = "Nachricht darf nicht leer sein")
    @Pattern(regexp = "[a-zA-ZÖöÜüÄä]+([ ]|[a-zA-ZÖöÜüÄä]|[0-9]|[.]|[,]|[(]|[)]|[-]|[/]|[^'\\u0027])*", message = "Pattern muss eingehalten werden")
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
        if (!(o instanceof ContactMessageDto)) return false;
        ContactMessageDto that = (ContactMessageDto) o;
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
        return "ContactMessageDto{" +
            "to=" + to +
            ", subject='" + subject + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
