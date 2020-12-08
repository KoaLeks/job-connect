package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/events")
@Transactional
public class EventEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final EventService eventService;
    private final EventMapper eventMapper;

    @Autowired
    public EventEndpoint(EventMapper eventMapper, EventService eventService) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Publish a new event")
    @CrossOrigin(origins = "http://localhost:4200")
    public EventInquiryDto create(@Valid @RequestBody EventInquiryDto eventInquiryDto) {
        LOGGER.info("POST /api/v1/events/{}", eventInquiryDto);

        return eventMapper.eventToEventInquiryDto(
            eventService.saveEvent(eventMapper.eventInquiryDtoToEvent(eventInquiryDto)));

    }

    @GetMapping(value = "/{id}/details")
    @ApiOperation(value = "Get event details")
    @CrossOrigin(origins = "http://localhost:4200")
    public DetailedEventDto getEventDetails(@PathVariable Long id) {
        LOGGER.info("GET /api/v1/events/{}/details", id);
        return eventMapper.eventToDetailedEventDto(eventService.findById(id));
    }

    @GetMapping
    @ApiOperation(value = "Get list of events")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<SimpleEventDto> findAll() {
        LOGGER.info("GET /api/v1/events");
        return eventMapper.eventsToSimpleEventsDtos(eventService.findAll());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update an event", authorizations = {@Authorization(value = "apiKey")})
    public EventInquiryDto update(@Valid @RequestBody EventInquiryDto eventInquiryDto) {
        LOGGER.info("PUT /api/v1/events/{}", eventInquiryDto);

        return eventMapper.eventToEventInquiryDto(
            eventService.saveEvent(eventMapper.eventInquiryDtoToEvent(eventInquiryDto)));

    }


}
