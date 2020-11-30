package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class EventDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
    private Employer employer;
    private Address address;
    private Set<Task> tasks;

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

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDto event = (EventDto) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "EventDto{" +
            "id=" + id +
            ", start=" + start +
            ", end=" + end +
            ", description='" + description + '\'' +
            ", employer=" + employer +
            ", address=" + address +
            ", tasks=" + tasks +
            '}';
    }

    public static final class EventDtoBuilder {
        private Long id;
        private LocalDateTime start;
        private LocalDateTime end;
        private String description;
        private Employer employer;
        private Address address;
        private Set<Task> tasks;

        private EventDtoBuilder() {
        }

        public static EventDto.EventDtoBuilder aEvent() {
            return new EventDto.EventDtoBuilder();
        }

        public EventDto.EventDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EventDto.EventDtoBuilder withStart(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public EventDto.EventDtoBuilder withEnd(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public EventDto.EventDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public EventDto.EventDtoBuilder withEmployer(Employer employer) {
            this.employer = employer;
            return this;
        }

        public EventDto.EventDtoBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public EventDto.EventDtoBuilder withTask(Set<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public EventDto build() {
            EventDto event = new EventDto();
            event.setDescription(description);
            event.setStart(start);
            event.setId(id);
            event.setEnd(end);
            event.setAddress(address);
            event.setEmployer(employer);
            event.setTasks(tasks);
            return event;
        }
    }


}
