package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

public class EventMapper {

    public EventDto entityToDto(Event event) {
        return EventDto.EventDtoBuilder.aEvent().
            withStart(event.getStart()).
            withEnd(event.getEnd()).
            withDescription(event.getDescription()).
            withEmployer(event.getEmployer()).
            withAddress(event.getAddress()).
            withTask(event.getTasks()).build();
    }

    public Event DtoToEntity(EventDto eventDto) {
        return Event.EventBuilder.aEvent().
            withStart(eventDto.getStart()).
            withEnd(eventDto.getEnd()).
            withDescription(eventDto.getDescription()).
            withEmployer(eventDto.getEmployer()).
            withAddress(eventDto.getAddress()).
            withTask(eventDto.getTasks()).build();
    }

}
