package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;


import java.time.LocalDateTime;
import java.util.Objects;

public class SimpleEventDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private String title;
    private String description;
    private Address address;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleEventDto simpleEvent = (SimpleEventDto) o;
        return Objects.equals(id, simpleEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SimpleEventDto{" +
            "id=" + id +
            ", start=" + start +
            ", end=" + end +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", address=" + address +
            '}';
    }



    public static final class SimpleEventDtoBuilder {
        private Long id;
        private LocalDateTime start;
        private LocalDateTime end;
        private String title;
        private String description;
        private Address address;

        private SimpleEventDtoBuilder() {
        }

        public static SimpleEventDto.SimpleEventDtoBuilder aEvent() {
            return new SimpleEventDto.SimpleEventDtoBuilder();
        }

        public SimpleEventDto.SimpleEventDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SimpleEventDto.SimpleEventDtoBuilder withStart(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public SimpleEventDto.SimpleEventDtoBuilder withEnd(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public SimpleEventDto.SimpleEventDtoBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public SimpleEventDto.SimpleEventDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SimpleEventDto.SimpleEventDtoBuilder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public SimpleEventDto build() {
            SimpleEventDto simpleEvent = new SimpleEventDto();
            simpleEvent.setTitle(title);
            simpleEvent.setDescription(description);
            simpleEvent.setStart(start);
            simpleEvent.setId(id);
            simpleEvent.setEnd(end);
            simpleEvent.setAddress(address);

            return simpleEvent;
        }
    }
}
