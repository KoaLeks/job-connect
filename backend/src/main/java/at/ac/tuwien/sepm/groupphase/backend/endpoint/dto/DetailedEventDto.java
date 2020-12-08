package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class DetailedEventDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private String title;
    private String description;
    private SimpleEmployerDto employer;
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

    public SimpleEmployerDto getEmployer() {
        return employer;
    }

    public void setEmployer(SimpleEmployerDto employer) {
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

    public static final class DetailedEventDtoBuilder {
        private Long id;
        private LocalDateTime start;
        private LocalDateTime end;
        private String title;
        private String description;
        private SimpleEmployerDto employer;
        private AddressInquiryDto address;
        private Set<SimpleTaskDto> tasks;

        private DetailedEventDtoBuilder() {
        }

        public static DetailedEventDtoBuilder aDetailedEventDto() {
            return new DetailedEventDtoBuilder();
        }

        public DetailedEventDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public DetailedEventDtoBuilder withStart(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public DetailedEventDtoBuilder withEnd(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public DetailedEventDtoBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public DetailedEventDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public DetailedEventDtoBuilder withEmployer(SimpleEmployerDto employer) {
            this.employer = employer;
            return this;
        }

        public DetailedEventDtoBuilder withAddress(AddressInquiryDto address) {
            this.address = address;
            return this;
        }

        public DetailedEventDtoBuilder withTasks(Set<SimpleTaskDto> tasks) {
            this.tasks = tasks;
            return this;
        }

        public DetailedEventDto build() {
            DetailedEventDto detailedEventDto = new DetailedEventDto();
            detailedEventDto.setId(id);
            detailedEventDto.setStart(start);
            detailedEventDto.setEnd(end);
            detailedEventDto.setTitle(title);
            detailedEventDto.setDescription(description);
            detailedEventDto.setEmployer(employer);
            detailedEventDto.setAddress(address);
            detailedEventDto.setTasks(tasks);
            return detailedEventDto;
        }
    }
}
