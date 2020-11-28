package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
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

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(value = "/api/v1/events")
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
    @ApiOperation(value = "Publish a new event", authorizations = {@Authorization(value = "apiKey")})
    public EventDto create(@Valid @RequestBody EventInquiryDto eventDto) {
        LOGGER.info("POST /api/v1/events/{}", eventDto);

        return eventMapper.eventToEventDto(
            eventService.saveEvent(eventMapper.eventInquiryDtoToEvent(eventDto)));

    }


}
