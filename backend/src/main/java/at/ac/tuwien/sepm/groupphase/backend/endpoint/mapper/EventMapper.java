package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TaskInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(uses = { EventService.class })
public interface EventMapper {

    @Named("simpleEvent")
    SimpleEventDto eventToSimpleEventDto(Event event);

    @IterableMapping(qualifiedByName = "simpleEvent")
    List<SimpleEventDto> eventsToSimpleEventsDtos(List<Event> events);

    Event eventInquiryDtoToEvent(EventInquiryDto eventInquiryDto);

    @Mapping(source = "eventId", target = "event")
    Task toTask(TaskInquiryDto taskInquiryDto);

    EventInquiryDto eventToEventInquiryDto(Event event);

    @Mapping(source = "event.id", target = "eventId")
    TaskInquiryDto toTaskInquiryDto(Task task);

}
