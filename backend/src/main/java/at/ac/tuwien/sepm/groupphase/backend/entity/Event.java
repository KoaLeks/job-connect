package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime start;
    @Column(nullable = false)
    private LocalDateTime end;
    @NotBlank
    @Column(nullable = false)
    private String title;
    @NotBlank
    @Column(nullable = false, length = 1000)
    private String description;
    @ManyToOne
    private Employer employer;
    @ManyToOne
    private Address address;
    @OneToMany(mappedBy = "event")
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

    public void setId(Long id) {
        this.id = id;
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
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
            Objects.equals(start, event.start) &&
            Objects.equals(end, event.end) &&
            Objects.equals(title, event.title) &&
            Objects.equals(description, event.description) &&
            Objects.equals(address, event.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, title, description, address);
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", start=" + start +
            ", end=" + end +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", employer=" + employer +
            ", address=" + address +
            '}';
    }

    public static final class EventBuilder{
        private Long id;
        private LocalDateTime start;
        private LocalDateTime end;
        private String title;
        private String description;
        private Employer employer;
        private Address address;
        private Set<Task> tasks;

        private EventBuilder(){}
        public static EventBuilder aEvent(){
            return new EventBuilder();
        }
        public EventBuilder withId(Long id){
            this.id = id;
            return this;
        }
        public EventBuilder withStart(LocalDateTime start){
            this.start = start;
            return this;
        }
        public EventBuilder withEnd(LocalDateTime end){
            this.end = end;
            return this;
        }
        public EventBuilder withTitle(String title){
            this.title = title;
            return this;
        }
        public EventBuilder withDescription(String description){
            this.description = description;
            return this;
        }
        public EventBuilder withEmployer(Employer employer){
            this.employer = employer;
            return this;
        }
        public EventBuilder withAddress(Address address){
            this.address = address;
            return this;
        }
        public EventBuilder withTask(Set<Task> tasks){
            this.tasks = tasks;
            return this;
        }

        public Event build(){
            Event event = new Event();
            event.setTitle(title);
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
