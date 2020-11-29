package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EventEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private final Address address = Address.AddressBuilder.aAddress()
        .withCity(CITY)
        .withState(STATE)
        .withZip(ZIP)
        .withAddressLine(ADDRESS_LINE)
        .withAdditional(ADDITIONAL)
        .build();

    private Event event = Event.EventBuilder.aEvent()
        .withStart(START)
        .withEnd(END)
        .withDescription(DESCRIPTION_EVENT)
        .withEmployer(EMPLOYER)
        .withAddress(address)
        .withTask(TASKS_EVENT)
        .build();

    @BeforeEach
    public void beforeEach() {
        eventRepository.deleteAll();
        addressRepository.deleteAll();
        event = Event.EventBuilder.aEvent()
            .withStart(START)
            .withEnd(END)
            .withDescription(DESCRIPTION_EVENT)
            .withEmployer(EMPLOYER)
            .withAddress(address)
            .withTask(TASKS_EVENT)
            .build();
    }

    @Test
    public void createValidEventTest() throws Exception {
        addressRepository.save(address);
        String body = objectMapper.writeValueAsString(eventMapper.eventToEventInquiryDto(event));

        MvcResult mvcResult = this.mockMvc.perform(post(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(eventRepository.count(), 1);
    }

    @Test
    public void tryCreateEventWithoutStart_End_Description_Employer_Address_Tasks_ShouldReturnBadRequest() throws Exception {
        event.setStart(null);
        event.setEnd(null);
        event.setDescription(null);
        event.setEmployer(null);
        event.setAddress(null);
        event.setTasks(null);

        String body = objectMapper.writeValueAsString(eventMapper.eventToEventInquiryDto(event));

        MvcResult mvcResult = this.mockMvc.perform(post(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                //Reads the errors from the body
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(6, errors.length);
            }
        );
    }

    @Test
    public void updateEventAddressToNull_ThenBadRequest() throws Exception {
        addressRepository.save(address);
        String body = objectMapper.writeValueAsString(eventMapper.eventToEventInquiryDto(event));

        MvcResult mvcResult = this.mockMvc.perform(post(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(eventRepository.count(), 1);

        EventDto eventResponse = objectMapper.readValue(response.getContentAsString(),
            EventDto.class);

        event.setId(1L);
        event.setAddress(null);
        String updateBody = objectMapper.writeValueAsString(eventMapper.eventToEventInquiryDto(event));

        MvcResult mvcUpdateResult = this.mockMvc.perform(put(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateBody))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse updateResponse = mvcUpdateResult.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), updateResponse.getStatus());
        assertEquals(eventRepository.count(), 1);
    }

    @Test
    public void updateEventStartAndEndValidTest() throws Exception {
        addressRepository.save(address);
        String body = objectMapper.writeValueAsString(eventMapper.eventToEventInquiryDto(event));

        MvcResult mvcResult = this.mockMvc.perform(post(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(eventRepository.count(), 1);

        event.setId(1L);
        event.setStart(LocalDateTime.of(2021, 12, 24, 17, 0, 0, 0));
        event.setEnd(LocalDateTime.of(2021, 12, 24, 23, 45, 0, 0));

        String updateBody = objectMapper.writeValueAsString(eventMapper.eventToEventInquiryDto(event));

        MvcResult mvcUpdateResult = this.mockMvc.perform(put(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateBody))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse updateResponse = mvcUpdateResult.getResponse();
        assertEquals(HttpStatus.OK.value(), updateResponse.getStatus());
        assertEquals(eventRepository.count(), 1);

        EventDto eventResponse = objectMapper.readValue(updateResponse.getContentAsString(),
            EventDto.class);
        assertEquals(LocalDateTime.of(2021, 12, 24, 17, 0, 0, 0),
            eventResponse.getStart());
        assertEquals(LocalDateTime.of(2021, 12, 24, 23, 45, 0, 0),
            eventResponse.getEnd());

    }

    @Test
    public void givenNothing_whenFindAll_thenEmptyEventList() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<SimpleEventDto> simpleEventDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleEventDto[].class));

        assertEquals(0, simpleEventDtos.size());
    }

    @Test
    public void givenOneEvent_whenFindAll_thenListWithSizeOneAndEventWithAllPropertiesExceptTasksAndEmployee()
        throws Exception {
        addressRepository.save(address);
        eventRepository.save(event);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<SimpleEventDto> simpleEventDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            SimpleEventDto[].class));

        assertEquals(1, simpleEventDtos.size());
        SimpleEventDto simpleEventDto = simpleEventDtos.get(0);
        assertAll(
            () -> assertEquals(event.getId(), simpleEventDto.getId()),
            () -> assertEquals(START, simpleEventDto.getStart()),
            () -> assertEquals(END, simpleEventDto.getEnd()),
            () -> assertEquals(DESCRIPTION_EVENT, simpleEventDto.getDescription()),
            () -> assertEquals(address, simpleEventDto.getAddress())
        );
    }

}


