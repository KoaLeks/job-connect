package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface EventMapper {

    @Named("Event")
    EventDto eventToEventDto(Event event);

    Event eventDtoToEvent(EventDto eventDto);

}
