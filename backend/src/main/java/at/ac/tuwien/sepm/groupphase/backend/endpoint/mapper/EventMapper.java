package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployerService;

import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(uses = { EventService.class, EmployerMapper.class, EmployerService.class})
public interface EventMapper {

    @Named("simpleEvent")
    SimpleEventDto eventToSimpleEventDto(Event event);

    @IterableMapping(qualifiedByName = "simpleEvent")
    List<SimpleEventDto> eventsToSimpleEventsDtos(List<Event> events);

    @Mapping(source = "employer.id", target = "employer")
    Event eventInquiryDtoToEvent(EventInquiryDto eventInquiryDto);

    @Mapping(source = "eventId", target = "event")
    Task toTask(TaskInquiryDto taskInquiryDto);

    EventInquiryDto eventToEventInquiryDto(Event event);

    DetailedEventDto eventToDetailedEventDto(Event event);

    @Mapping(source = "event.id", target = "eventId")
    TaskInquiryDto toTaskInquiryDto(Task task);

}
