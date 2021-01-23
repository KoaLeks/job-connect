package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.RegisterEmployerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private EmployerRepository employerRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RegisterEmployerMapper registerEmployerMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EventService eventService;

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

    private final Address address2 = Address.AddressBuilder.aAddress()
        .withCity(CITY)
        .withState("Vienna")
        .withZip(ZIP)
        .withAddressLine(ADDRESS_LINE)
        .withAdditional(ADDITIONAL)
        .build();

    private Event event = Event.EventBuilder.aEvent()
        .withStart(START)
        .withEnd(END)
        .withTitle(TITLE_EVENT)
        .withDescription(DESCRIPTION_EVENT)
        .withEmployer(EMPLOYER)
        .withAddress(address)
        .withTask(TASKS_EVENT)
        .build();

    private Event event2 = Event.EventBuilder.aEvent()
        .withStart(START)
        .withEnd(END)
        .withTitle("Eventtitle2")
        .withDescription(DESCRIPTION_EVENT)
        .withEmployer(EMPLOYER)
        .withAddress(address)
        .withTask(TASKS_EVENT)
        .build();

    private Employer employer = Employer.EmployerBuilder.aEmployer()
        .withProfile(Profile.ProfileBuilder.aProfile()
            .isEmployer(true)
            .withEmail(EMPLOYER_EMAIL)
            .withName(EMPLOYER_LAST_NAME)
            .withForename(EMPLOYER_FIRST_NAME)
            .withPassword(EMPLOYER_PASSWORD)
            .build())
        .withCompanyName(EMPLOYER_COMPANY_NAME)
        .withDescription(EMPLOYER_COMPANY_DESCRIPTION)
        .build();

    private final Task task = Task.TaskBuilder.aTask()
        .withDescription(DESCRIPTION_TASK)
        .withEmployeeCount(EMPLOYEE_COUNT)
        .withPaymentHourly(PAYMENT_HOURLY)
        .withEvent(EVENT)
        .withEmployees(EMPLOYEES)
        .withInterestArea(INTEREST_AREA)
        .build();


    @BeforeEach
    public void beforeEach() {
        eventRepository.deleteAll();
        addressRepository.deleteAll();
        employerRepository.deleteAll();
        profileRepository.deleteAll();
        event = Event.EventBuilder.aEvent()
            .withStart(START)
            .withEnd(END)
            .withTitle(TITLE_EVENT)
            .withDescription(DESCRIPTION_EVENT)
            .withEmployer(EMPLOYER)
            .withAddress(address)
            .withTask(TASKS_EVENT)
            .build();
    }

    @Test
    public void createValidEventTest() throws Exception {

        Profile profile = profileRepository.save(employer.getProfile());
        employer.setProfile(profile);
        employer.setId(profile.getId());
        Employer employer1 = employerRepository.save(employer);

        event.setEmployer(employer1);

        String body = objectMapper.writeValueAsString(eventMapper.eventToEventInquiryDto(event));

        MvcResult mvcResult = this.mockMvc.perform(post(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(eventRepository.count(), 1);
    }

    @Test
    public void tryCreateEventWithoutStart_End_Title_Description_Employer_Address_Tasks_ShouldReturnBadRequest() throws Exception {
        event.setStart(null);
        event.setEnd(null);
        event.setTitle(null);
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
                String[] errors = content.split(",");
                assertEquals(8, errors.length);
            }
        );
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

        List<DetailedEventDto> simpleEventDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            DetailedEventDto[].class));

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

        List<DetailedEventDto> simpleEventDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            DetailedEventDto[].class));

        assertEquals(1, simpleEventDtos.size());
        DetailedEventDto simpleEventDto = simpleEventDtos.get(0);
        assertAll(
            () -> assertEquals(event.getId(), simpleEventDto.getId()),
            () -> assertEquals(START, simpleEventDto.getStart()),
            () -> assertEquals(END, simpleEventDto.getEnd()),
            () -> assertEquals(TITLE_EVENT, simpleEventDto.getTitle()),
            () -> assertEquals(DESCRIPTION_EVENT, simpleEventDto.getDescription()),
            () -> assertEquals(address.getId(), simpleEventDto.getAddress().getId())
        );
    }

    @Test
    public void deleteValidEventAndAllRelatedData() throws Exception {

        Profile profile = profileRepository.save(employer.getProfile());
        employer.setProfile(profile);
        employer.setId(profile.getId());
        Employer employer1 = employerRepository.save(employer);

        event.setEmployer(employer1);

        Set<Task> tasks = new HashSet<>();
        tasks.add(task);
        event.setTasks(tasks);

        Long id = eventService.saveEvent(event).getId();

        //checks if the event and all the related data has been stored
        assertEquals(eventRepository.count(), 1);
        assertEquals(addressRepository.count(), 1);
        assertEquals(taskRepository.count(), 1);

        MvcResult mvcResult = this.mockMvc.perform(delete(EVENTS_BASE_URI + "/" + id)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        //checks if the event and all the related data has been deleted
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(0, eventRepository.count());
        assertEquals(0, addressRepository.count());
        assertEquals(0, taskRepository.count());
    }

    @Test
    public void searchForEventWithValidTitle() throws Exception {
        addressRepository.save(address);

        eventRepository.save(event);
        eventRepository.save(event2);

        assertEquals(addressRepository.count(), 1);
        assertEquals(eventRepository.count(), 2);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENTS_BASE_URI)
            .queryParam("title", "Flyer"))
            .andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<Event> foundEvents = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            Event[].class));

        assertEquals(foundEvents.size(), 1);
        assert(foundEvents.contains(event));
        assert(!foundEvents.contains(event2));

    }

    @Test
    public void searchForEventWithValidState() throws Exception {
        addressRepository.save(address);
        addressRepository.save(address2);

        eventRepository.save(event);

        event2.setAddress(address2);
        eventRepository.save(event2);

        assertEquals(addressRepository.count(), 2);
        assertEquals(eventRepository.count(), 2);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENTS_BASE_URI)
            .queryParam("state", "Upper Austria"))
            .andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<Event> foundEvents = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            Event[].class));

        assertEquals(foundEvents.size(), 1);
        assert(foundEvents.contains(event));
        assert(!foundEvents.contains(event2));

    }

    @Test
    public void searchForEventWithValidStateAndValidTitle() throws Exception {
        addressRepository.save(address);
        addressRepository.save(address2);

        eventRepository.save(event);

        event2.setAddress(address2);
        eventRepository.save(event2);

        assertEquals(addressRepository.count(), 2);
        assertEquals(eventRepository.count(), 2);

        MvcResult mvcResult = this.mockMvc.perform(get(EVENTS_BASE_URI)
            .queryParam("state", "Upper Austria")
            .queryParam("title", "Flyer"))
            .andDo(print()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<Event> foundEvents = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            Event[].class));

        assertEquals(foundEvents.size(), 1);
        assert(foundEvents.contains(event));
        assert(!foundEvents.contains(event2));

    }
}


