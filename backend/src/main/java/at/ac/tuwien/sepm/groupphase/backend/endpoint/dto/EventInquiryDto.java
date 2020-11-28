package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class EventInquiryDto {
    @NotNull(message = "must not be null")
    @Future
    private LocalDateTime start;

    @NotNull(message = "must not be null")
    @Future
    private LocalDateTime end;

    @NotNull(message = "must not be null")
    @NotBlank(message = "must not be empty")
    @Size(max = 1000)
    private String description;

    @NotNull(message = "must not be null")
    private Address address;

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

    @AssertTrue(message = "Start Date must be earlier than End Date")
    public boolean isValidDate() {
        return end.isAfter(start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventInquiryDto)) return false;
        EventInquiryDto that = (EventInquiryDto) o;
        return Objects.equals(start, that.start) &&
            Objects.equals(end, that.end) &&
            Objects.equals(description, that.description) &&
            Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, description, address);
    }

    @Override
    public String toString() {
        return "EventInquiryDto{" +
            "start=" + start +
            ", end=" + end +
            ", description='" + description + '\'' +
            ", address=" + address +
            '}';
    }

    public static final class EventInquiryDtoBuilder {
        private LocalDateTime start;
        private LocalDateTime end;
        private String description;
        private Address address;

        private EventInquiryDtoBuilder() {
        }

        public static EventInquiryDtoBuilder aEvent() {
            return new EventInquiryDtoBuilder();
        }

        public EventInquiryDtoBuilder withStart(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public EventInquiryDtoBuilder withEnd(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public EventInquiryDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public EventInquiryDtoBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public EventInquiryDto build() {
            EventInquiryDto event = new EventInquiryDto();
            event.setDescription(description);
            event.setStart(start);
            event.setEnd(end);
            event.setAddress(address);
            return event;
        }
    }


}
