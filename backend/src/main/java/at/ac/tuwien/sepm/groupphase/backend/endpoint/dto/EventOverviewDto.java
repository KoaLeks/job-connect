package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class EventOverviewDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private String title;
    private String description;
    private SuperSimpleEmployerDto employer;
    private AddressInquiryDto address;
    private Set<SimpleTaskDto> tasks;

    @Override
    public String toString() {
        return "DetailedEventDto{" +
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SuperSimpleEmployerDto getEmployer() {
        return employer;
    }

    public void setEmployer(SuperSimpleEmployerDto employer) {
        this.employer = employer;
    }

    public AddressInquiryDto getAddress() {
        return address;
    }

    public void setAddress(AddressInquiryDto address) {
        this.address = address;
    }

    public Set<SimpleTaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(Set<SimpleTaskDto> tasks) {
        this.tasks = tasks;
    }

    public static final class EventOverviewDtoBuilder {
        private Long id;
        private LocalDateTime start;
        private LocalDateTime end;
        private String title;
        private String description;
        private SuperSimpleEmployerDto employer;
        private AddressInquiryDto address;
        private Set<SimpleTaskDto> tasks;

        private EventOverviewDtoBuilder() {
        }

        public static EventOverviewDtoBuilder anEventOverviewDto() {
            return new EventOverviewDtoBuilder();
        }

        public EventOverviewDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public EventOverviewDtoBuilder withStart(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public EventOverviewDtoBuilder withEnd(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public EventOverviewDtoBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public EventOverviewDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public EventOverviewDtoBuilder withEmployer(SuperSimpleEmployerDto employer) {
            this.employer = employer;
            return this;
        }

        public EventOverviewDtoBuilder withAddress(AddressInquiryDto address) {
            this.address = address;
            return this;
        }

        public EventOverviewDtoBuilder withTasks(Set<SimpleTaskDto> tasks) {
            this.tasks = tasks;
            return this;
        }

        public EventOverviewDto build() {
            EventOverviewDto eventOverviewDto = new EventOverviewDto();
            eventOverviewDto.setId(id);
            eventOverviewDto.setStart(start);
            eventOverviewDto.setEnd(end);
            eventOverviewDto.setTitle(title);
            eventOverviewDto.setDescription(description);
            eventOverviewDto.setEmployer(employer);
            eventOverviewDto.setAddress(address);
            eventOverviewDto.setTasks(tasks);
            return eventOverviewDto;
        }
    }
}
