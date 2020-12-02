package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class EventInquiryDto {

    private Long id;

    @NotNull(message = "must not be null")
    @Future
    private LocalDateTime start;

    @NotNull(message = "must not be null")
    @Future
    private LocalDateTime end;

    @NotNull(message = "must not be null")
    @NotBlank(message = "must not be empty")
    @Size(max = 255)
    private String title;

    @NotNull(message = "must not be null")
    @NotBlank(message = "must not be empty")
    @Size(max = 1000)
    private String description;

    private Employer employer;

    @NotNull(message = "must not be null")
    private Address address;

    private Set<Task> tasks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @AssertTrue(message = "Start Date must be earlier than End Date")
    public boolean isValidDate() {
        if (end != null && start != null) {
            return end.isAfter(start);
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventInquiryDto)) return false;
        EventInquiryDto that = (EventInquiryDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EventInquiryDto{" +
            "id=" + id +
            ", start=" + start +
            ", end=" + end +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", employer=" + employer +
            ", address=" + address +
            ", tasks=" + tasks +
            '}';
    }

    public static final class EventInquiryDtoBuilder {
        private Long id;
        private LocalDateTime start;
        private LocalDateTime end;
        private String title;
        private String description;
        private Employer employer;
        private Address address;
        private Set<Task> tasks;

        private EventInquiryDtoBuilder() {
        }

        public static EventInquiryDtoBuilder aEvent() {
            return new EventInquiryDtoBuilder();
        }

        public EventInquiryDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EventInquiryDtoBuilder withStart(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public EventInquiryDtoBuilder withEnd(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public EventInquiryDtoBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public EventInquiryDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public EventInquiryDtoBuilder withEmployer(Employer employer) {
            this.employer = employer;
            return this;
        }

        public EventInquiryDtoBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public EventInquiryDtoBuilder withTask(Set<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public EventInquiryDto build() {
            EventInquiryDto event = new EventInquiryDto();
            event.setId(id);
            event.setTitle(title);
            event.setDescription(description);
            event.setStart(start);
            event.setEnd(end);
            event.setEmployer(employer);
            event.setAddress(address);
            event.setTasks(tasks);
            return event;
        }
    }


}
