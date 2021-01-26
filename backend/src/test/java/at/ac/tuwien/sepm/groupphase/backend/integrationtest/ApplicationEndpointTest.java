package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ApplicationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ApplicationStatusDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNotificationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ApplicationStatusMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NotificationMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.util.NotificationType;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ApplicationEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private Employee_TasksRepository employee_tasksRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private ApplicationStatusMapper applicationStatusMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private Employee employee = Employee.EmployeeBuilder.aEmployee()
        .withProfile(Profile.ProfileBuilder.aProfile()
            .isEmployer(false)
            .withEmail(EMPLOYEE_EMAIL)
            .withName(EMPLOYEE_LAST_NAME)
            .withForename(EMPLOYEE_FIRST_NAME)
            .withPassword(EMPLOYEE_PASSWORD)
            .build())
        .withGender(EMPLOYEE_GENDER)
        .withBirthDate(EMPLOYEE_BIRTH_DATE)
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

    private Employer employer2 = Employer.EmployerBuilder.aEmployer()
        .withProfile(Profile.ProfileBuilder.aProfile()
            .isEmployer(true)
            .withEmail(EMPLOYER_EMAIL_2)
            .withName(EMPLOYER_LAST_NAME)
            .withForename(EMPLOYER_FIRST_NAME)
            .withPassword(EMPLOYER_PASSWORD)
            .build())
        .withCompanyName(EMPLOYER_COMPANY_NAME)
        .withDescription(EMPLOYER_COMPANY_DESCRIPTION)
        .build();

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
        .withTitle(TITLE_EVENT)
        .withDescription(DESCRIPTION_EVENT)
        .withEmployer(employer)
        .withAddress(address)
        .withTask(TASKS_EVENT)
        .build();

    private Task task = Task.TaskBuilder.aTask()
        .withDescription(DESCRIPTION_TASK)
        .withEmployees(EMPLOYEES)
        .withEvent(EVENT)
        .withInterestArea(INTEREST_AREA)
        .withPaymentHourly(PAYMENT_HOURLY)
        .withEmployeeCount(EMPLOYEE_COUNT)
        .build();

    private Notification application = Notification.NotificationBuilder.aNotification()
        .withEvent(null)
        .withFavorite(false)
        .withMessage("Test Application 1")
        .withSeen(false)
        .withRecipient(employer.getProfile())
        .withSender(employee.getProfile())
        .withTask(null)
        .withType(NotificationType.APPLICATION.name())
        .build();

    private ApplicationDto applicationDto = ApplicationDto.ApplicationDtoBuilder.aApplicationDto()
        .withMessage("Test application")
        .withTask(1L)
        .build();

    private Set<Event> events = Collections.emptySet();
    private Set<Task> tasks = Collections.emptySet();

    @BeforeEach
    public void beforeEach() {
        notificationRepository.deleteAll();
        eventRepository.deleteAll();
        employerRepository.deleteAll();
        employeeRepository.deleteAll();
        profileRepository.deleteAll();
//1        notificationRepository.deleteAll();
//2        eventRepository.deleteAll();
//3        employerRepository.deleteAll();
//4        employeeRepository.deleteAll();
//5        profileRepository.deleteAll();

        tasks = new HashSet<>();
        events = new HashSet<>();

        employee = Employee.EmployeeBuilder.aEmployee()
            .withProfile(Profile.ProfileBuilder.aProfile()
                .isEmployer(false)
                .withId(null)
                .withEmail(EMPLOYEE_EMAIL)
                .withName(EMPLOYEE_LAST_NAME)
                .withForename(EMPLOYEE_FIRST_NAME)
                .withPassword(EMPLOYEE_PASSWORD)
                .build())
            .withGender(EMPLOYEE_GENDER)
            .withBirthDate(EMPLOYEE_BIRTH_DATE)
            .build();

        employer = Employer.EmployerBuilder.aEmployer()
            .withProfile(Profile.ProfileBuilder.aProfile()
                .isEmployer(false)
                .withId(null)
                .withEmail(EMPLOYER_EMAIL)
                .withName(EMPLOYER_LAST_NAME)
                .withForename(EMPLOYER_FIRST_NAME)
                .withPassword(EMPLOYER_PASSWORD)
                .withPublicInfo(EMPLOYER_PUBLIC_INFO)
                .build())
            .withCompanyName(EMPLOYER_COMPANY_NAME)
            .withDescription(EMPLOYER_COMPANY_DESCRIPTION)
            .withEvents(events)
            .build();

        event = Event.EventBuilder.aEvent()
            .withStart(START)
            .withEnd(END)
            .withTitle(TITLE_EVENT)
            .withDescription(DESCRIPTION_EVENT)
            .withEmployer(EMPLOYER)
            .withAddress(address)
            .withTask(tasks)
            .build();

        application = Notification.NotificationBuilder.aNotification()
            .withEvent(event)
            .withFavorite(false)
            .withMessage("Test Application 1")
            .withSeen(false)
            .withRecipient(employer.getProfile())
            .withSender(employee.getProfile())
            .withTask(task)
            .withType(NotificationType.APPLICATION.name())
            .build();
    }

    @Test
    public void createInvalidApplicationShouldReturnBAD_REQUEST() throws Exception {
        addressRepository.save(address);
        task.setId(taskRepository.save(task).getId());
        employee.getProfile().setId(employeeRepository.save(employee).getId());
        employer.getProfile().setId(employerRepository.save(employer).getId());
        event.setEmployer(employer);
        tasks.add(task);

        event.setId(eventRepository.save(event).getId());
        event.setTasks(tasks);
        task.setEvent(event);
        taskRepository.save(task);
        eventRepository.save(event);

        events.add(event);
        employer.setEvents(events);
        employerRepository.save(employer);

        applicationDto.setTask(null);
        String body = objectMapper.writeValueAsString(applicationDto);

        MvcResult mvcResult = this.mockMvc.perform(put(APPLICATION_BASE_URI + "/apply")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus())
        );
    }

    @Test
    public void createValidApplicationShouldReturnCreated() throws Exception {
        addressRepository.save(address);
        task.setId(taskRepository.save(task).getId());
        employee.getProfile().setId(employeeRepository.save(employee).getId());
        employer.getProfile().setId(employerRepository.save(employer).getId());
        event.setEmployer(employer);
        tasks.add(task);

        event.setId(eventRepository.save(event).getId());
        event.setTasks(tasks);
        task.setEvent(event);
        taskRepository.save(task);
        eventRepository.save(event);

        events.add(event);
        employer.setEvents(events);
        employerRepository.save(employer);

        applicationDto.setTask(task.getId());
        String body = objectMapper.writeValueAsString(applicationDto);

        MvcResult mvcResult = this.mockMvc.perform(put(APPLICATION_BASE_URI + "/apply")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertAll(
            () -> assertEquals(HttpStatus.CREATED.value(), response.getStatus()),
            () -> assertEquals(1L, employee_tasksRepository.count())
        );
    }

    @Test
    public void acceptingApplicationShouldChangeApplicationStatusAndReturnNoContent() throws Exception {
        addressRepository.save(address);
        task.setId(taskRepository.save(task).getId());
        employee.getProfile().setId(employeeRepository.save(employee).getId());
        employer.getProfile().setId(employerRepository.save(employer).getId());
        event.setEmployer(employer);
        tasks.add(task);

        event.setId(eventRepository.save(event).getId());
        event.setTasks(tasks);
        task.setEvent(event);
        taskRepository.save(task);
        eventRepository.save(event);

        events.add(event);
        employer.setEvents(events);
        employerRepository.save(employer);

        application.setId(notificationRepository.save(application).getId());

        Employee_Tasks employee_tasks = Employee_Tasks.Employee_TasksBuilder.anEmployee_Tasks()
            .withTask(task)
            .withAccepted(null)
            .withEmployee(employee)
            .build();
        employee_tasks.setId(employee_tasksRepository.save(employee_tasks).getId());

        ApplicationStatusDto applicationStatusDto = ApplicationStatusDto.ApplicationStatusDtoBuilder.aApplicationStatusDto()
            .isAccepted(true)
            .withEmployee(employee.getId())
            .withTask(task.getId())
            .withNotification(application.getId())
            .build();

        String body = objectMapper.writeValueAsString(applicationStatusDto);

        MvcResult mvcResult = this.mockMvc.perform(post(APPLICATION_BASE_URI + "/changeStatus")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertAll(
            () -> assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus())
        );

    }

    @Test
    public void changingApplicationStatusAsWrongEmployerShouldThrowUnauthorized() throws Exception {
        addressRepository.save(address);
        task.setId(taskRepository.save(task).getId());
        employee.getProfile().setId(employeeRepository.save(employee).getId());
        employer.getProfile().setId(employerRepository.save(employer).getId());
        employer2.getProfile().setId(employerRepository.save(employer2).getId());
        event.setEmployer(employer);
        tasks.add(task);

        event.setId(eventRepository.save(event).getId());
        event.setTasks(tasks);
        task.setEvent(event);
        taskRepository.save(task);
        eventRepository.save(event);

        events.add(event);
        employer.setEvents(events);
        employerRepository.save(employer);

        application.setId(notificationRepository.save(application).getId());

        Employee_Tasks employee_tasks = Employee_Tasks.Employee_TasksBuilder.anEmployee_Tasks()
            .withTask(task)
            .withAccepted(null)
            .withEmployee(employee)
            .build();
        employee_tasks.setId(employee_tasksRepository.save(employee_tasks).getId());

        ApplicationStatusDto applicationStatusDto = ApplicationStatusDto.ApplicationStatusDtoBuilder.aApplicationStatusDto()
            .isAccepted(true)
            .withEmployee(employee.getId())
            .withTask(task.getId())
            .withNotification(application.getId())
            .build();

        String body = objectMapper.writeValueAsString(applicationStatusDto);

        MvcResult mvcResult = this.mockMvc.perform(post(APPLICATION_BASE_URI + "/changeStatus")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL_2, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertAll(
            () -> assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus())
        );
    }

    @Test
    public void getApplicationForOneEventShouldReturnListOfApplicationsAndOK() throws Exception {
        addressRepository.save(address);
        task.setId(taskRepository.save(task).getId());
        employee.getProfile().setId(employeeRepository.save(employee).getId());
        employer.getProfile().setId(employerRepository.save(employer).getId());
        event.setEmployer(employer);
        tasks.add(task);

        event.setId(eventRepository.save(event).getId());
        event.setTasks(tasks);
        task.setEvent(event);
        taskRepository.save(task);
        eventRepository.save(event);

        events.add(event);
        employer.setEvents(events);
        employerRepository.save(employer);
        application.setId(notificationRepository.save(application).getId());

        Employee_Tasks employee_tasks = Employee_Tasks.Employee_TasksBuilder.anEmployee_Tasks()
            .withTask(task)
            .withAccepted(null)
            .withEmployee(employee)
            .build();
        employee_tasks.setId(employee_tasksRepository.save(employee_tasks).getId());


        MvcResult mvcResult = this.mockMvc.perform(get(APPLICATION_BASE_URI + "/events/" + event.getId())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL_2, ADMIN_ROLES))
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        SimpleNotificationDto[] list = objectMapper.readValue(response.getContentAsString(), SimpleNotificationDto[].class);

        assertAll(
            () -> assertEquals(1, list.length),
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus())
        );

    }

    @Test
    public void getApplicationForNonExistingEventShouldReturnEmptyListAndOK() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(APPLICATION_BASE_URI + "/events/" + 999)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES))
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        SimpleNotificationDto[] list = objectMapper.readValue(response.getContentAsString(), SimpleNotificationDto[].class);

        assertAll(
            () -> assertEquals(0, list.length),
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus())
        );
    }

    @Test
    public void getAppliedEventsShouldReturnListOfEventsAndOK() throws Exception {
        addressRepository.save(address);
        task.setId(taskRepository.save(task).getId());
        employee.getProfile().setId(employeeRepository.save(employee).getId());
        employer.getProfile().setId(employerRepository.save(employer).getId());
        event.setEmployer(employer);
        tasks.add(task);

        event.setId(eventRepository.save(event).getId());
        event.setTasks(tasks);
        task.setEvent(event);
        taskRepository.save(task);
        eventRepository.save(event);

        events.add(event);
        employer.setEvents(events);
        employerRepository.save(employer);

        Employee_Tasks employee_tasks = Employee_Tasks.Employee_TasksBuilder.anEmployee_Tasks()
            .withTask(task)
            .withAccepted(null)
            .withEmployee(employee)
            .build();
        employee_tasks.setId(employee_tasksRepository.save(employee_tasks).getId());


        MvcResult mvcResult = this.mockMvc.perform(get(APPLICATION_BASE_URI + "/applied")
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        DetailedEventDto[] list = objectMapper.readValue(response.getContentAsString(), DetailedEventDto[].class);

        assertAll(
            () -> assertEquals(1, list.length),
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus())
        );
    }

    @Test
    public void deletingNonExistingApplicationShouldReturnNotFound() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(delete(APPLICATION_BASE_URI + "/" + 999)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES))
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertAll(
            () -> assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus())
        );
    }

    @Test
    public void deletingApplicationShouldReduceApplicationListByOneAndReturnOK() throws Exception {
        addressRepository.save(address);
        task.setId(taskRepository.save(task).getId());
        employee.getProfile().setId(employeeRepository.save(employee).getId());
        employer.getProfile().setId(employerRepository.save(employer).getId());
        event.setEmployer(employer);
        tasks.add(task);

        event.setId(eventRepository.save(event).getId());
        event.setTasks(tasks);
        task.setEvent(event);
        taskRepository.save(task);
        eventRepository.save(event);

        events.add(event);
        employer.setEvents(events);
        employerRepository.save(employer);
        application.setId(notificationRepository.save(application).getId());

        MvcResult mvcResult = this.mockMvc.perform(delete(APPLICATION_BASE_URI + "/" + application.getId())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(0, notificationRepository.count())
        );
    }


    @Test
    public void deletingJobShouldReduceNotificationListAndEmployee_TaskListByOneAndReturnOK() throws Exception {
        addressRepository.save(address);
        task.setId(taskRepository.save(task).getId());
        employee.getProfile().setId(employeeRepository.save(employee).getId());
        employer.getProfile().setId(employerRepository.save(employer).getId());
        event.setEmployer(employer);
        tasks.add(task);

        event.setId(eventRepository.save(event).getId());
        event.setTasks(tasks);
        task.setEvent(event);
        taskRepository.save(task);
        eventRepository.save(event);

        events.add(event);
        employer.setEvents(events);
        employerRepository.save(employer);

        application.setTask(task);
        application.setRecipient(employee.getProfile());
        application.setSender(employer.getProfile());
        application.setMessage("Sie wurden akzeptiert");
        application.setType(NotificationType.EVENT_ACCEPTED.name());
        application.setId(notificationRepository.save(application).getId());


        Employee_Tasks employee_tasks = Employee_Tasks.Employee_TasksBuilder.anEmployee_Tasks()
            .withTask(task)
            .withAccepted(true)
            .withEmployee(employee)
            .build();
        employee_tasks.setId(employee_tasksRepository.save(employee_tasks).getId());

        MvcResult mvcResult = this.mockMvc.perform(delete(APPLICATION_BASE_URI + "/task/" + task.getId())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();



        assertAll(
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
            () -> assertEquals(0, notificationRepository.count()),
            () -> assertEquals(0, employee_tasksRepository.count())
        );
    }
}
