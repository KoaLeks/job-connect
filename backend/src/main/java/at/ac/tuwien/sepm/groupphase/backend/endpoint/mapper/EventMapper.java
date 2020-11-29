package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface EventMapper {

    @Named("Event")
    EventDto eventToEventDto(Event event);

    Event eventDtoToEvent(EventDto eventDto);

    @Named("simpleEvent")
    SimpleEventDto eventToSimpleEventDto(Event event);

    @IterableMapping(qualifiedByName = "simpleEvent")
    List<SimpleEventDto> eventsToSimpleEventsDtos(List<Event> events);

}
