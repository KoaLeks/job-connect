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

@Mapper(uses = { EventService.class, EmployeeMapper.class, EmployerMapper.class, EmployerService.class})
public interface EventMapper {


    @IterableMapping(qualifiedByName = "detailedEvent")
    List<DetailedEventDto> eventsToDetailedEventDtos(List<Event> events);

    @IterableMapping(qualifiedByName = "eventOverview")
    List<EventOverviewDto> eventsToEventOverviewDtos(List<Event> events);

    @Mapping(source = "employer.id", target = "employer")
    Event eventInquiryDtoToEvent(EventInquiryDto eventInquiryDto);

    @Mapping(source = "eventId", target = "event")
    Task toTask(TaskInquiryDto taskInquiryDto);

    EventInquiryDto eventToEventInquiryDto(Event event);

    @Named("detailedEvent")
    DetailedEventDto eventToDetailedEventDto(Event event);

    @Named("eventOverview")
    EventOverviewDto eventToEventOverviewDto(Event event);

    @Mapping(source = "employee_tasks.employee", target = "employee")
    @Mapping(source = "employee_tasks.task.id", target = "taskId")
    @Mapping(source = "employee_tasks.accepted", target = "accepted")
    Employee_TasksDto toEmployee_TasksDto(Employee_Tasks employee_tasks);

    @Mapping(source = "event.id", target = "eventId")
    TaskInquiryDto toTaskInquiryDto(Task task);

}
