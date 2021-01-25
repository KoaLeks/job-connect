package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNotificationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NotificationMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.util.NotificationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class NotificationEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NotificationMapper notificationMapper;

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

    private Task task = Task.TaskBuilder.aTask()
        .withDescription(DESCRIPTION_TASK)
        .withEmployees(EMPLOYEES)
        .withEvent(EVENT)
        .withInterestArea(INTEREST_AREA)
        .withPaymentHourly(PAYMENT_HOURLY)
        .withEmployeeCount(EMPLOYEE_COUNT)
        .build();

    private Notification notification1 = Notification.NotificationBuilder.aNotification()
        .withEvent(null)
        .withFavorite(false)
        .withMessage("Test Notification 1")
        .withSeen(false)
        .withRecipient(employee.getProfile())
        .withSender(null)
        .withTask(null)
        .withType(NotificationType.NOTIFICATION.name())
        .build();

    private Notification notification2 = Notification.NotificationBuilder.aNotification()
        .withEvent(null)
        .withFavorite(false)
        .withMessage("Test Notification 2")
        .withSeen(false)
        .withRecipient(employee.getProfile())
        .withSender(null)
        .withTask(null)
        .withType(NotificationType.NOTIFICATION.name())
        .build();

    private Notification notification3 = Notification.NotificationBuilder.aNotification()
        .withEvent(null)
        .withFavorite(false)
        .withMessage("Test Notification 3")
        .withSeen(false)
        .withRecipient(employer.getProfile())
        .withSender(null)
        .withTask(task)
        .withType(NotificationType.NOTIFICATION.name())
        .build();

    @BeforeEach
    public void beforeEach() {
        employeeRepository.deleteAll();
        employerRepository.deleteAll();
        notificationRepository.deleteAll();
        taskRepository.deleteAll();
        profileRepository.deleteAll();

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
            .build();

        notification1 = Notification.NotificationBuilder.aNotification()
            .withEvent(null)
            .withFavorite(false)
            .withMessage("Test Notification 1")
            .withSeen(false)
            .withRecipient(employee.getProfile())
            .withSender(null)
            .withTask(null)
            .withType(NotificationType.NOTIFICATION.name())
            .build();

        notification2 = Notification.NotificationBuilder.aNotification()
            .withEvent(null)
            .withFavorite(false)
            .withMessage("Test Notification 2")
            .withSeen(false)
            .withRecipient(employee.getProfile())
            .withSender(null)
            .withTask(null)
            .withType(NotificationType.NOTIFICATION.name())
            .build();

        notification3 = Notification.NotificationBuilder.aNotification()
            .withEvent(null)
            .withFavorite(false)
            .withMessage("Test Notification 3")
            .withSeen(false)
            .withRecipient(employer.getProfile())
            .withSender(null)
            .withTask(task)
            .withType(NotificationType.NOTIFICATION.name())
            .build();
    }

    @Test
    public void getNotificationsFromNonExistingUserShouldReturnNotFound() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get(NOTIFICATION_BASE_URI)
            .accept(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void getNotificationsShouldReturnList() throws Exception {
        employee.getProfile().setEmail(EMPLOYEE_EMAIL_2);
        employeeRepository.save(employee).getId();
        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
        MvcResult mvcResult = this.mockMvc.perform(get(NOTIFICATION_BASE_URI)
            .accept(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL_2, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        SimpleNotificationDto[] list = objectMapper.readValue(response.getContentAsString(), SimpleNotificationDto[].class);
        assertAll(
            () -> assertEquals(2, list.length),
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus())
        );
    }

    @Test
    public void deleteNonExistingNotificationsShouldReturnNotFound() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(delete(NOTIFICATION_BASE_URI + "/" + 999)
            .accept(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void deletingNotificationsShouldReduceNumberOfNotificationsByOne() throws Exception {
        employeeRepository.save(employee);
        Long id = notificationRepository.save(notification1).getId();
        notificationRepository.save(notification2);
        long countBeforeDelete = notificationRepository.count();
        MvcResult mvcResult = this.mockMvc.perform(delete(NOTIFICATION_BASE_URI + "/" + id)
            .accept(MediaType.APPLICATION_JSON)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(countBeforeDelete - 1, notificationRepository.count());
    }

    @Test
    public void favoritingNotificationShouldChangeFavoriteStatusAndReturnOK() throws Exception {
        employer.getProfile().setId(employerRepository.save(employer).getId());
        task.setId(taskRepository.save(task).getId());
        notification3.setTask(task);
        notification3.setRecipient(employer.getProfile());
        Long id = notificationRepository.save(notification3).getId();
        boolean favoriteStatusBeforeChange = notification3.getFavorite();
        String body = objectMapper.writeValueAsString(notificationMapper.notificationToSimpleNotificationDto(notification3));
        MvcResult mvcResult = this.mockMvc.perform(put(CHANGE_FAVORITE_NOTIFICATION_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYER_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertNotEquals(favoriteStatusBeforeChange, notificationRepository.findById(id).get().getFavorite()),
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus())
        );
    }

    @Test
    public void updatingNotificationShouldReturnDifferentNotificationAndOK() throws Exception {
        employee.setId(null);
        employee.getProfile().setId(null);
        employee.getProfile().setId(employeeRepository.save(employee).getId());
        task.setId(taskRepository.save(task).getId());
        notification2.setTask(task);
        notification2.setRecipient(employee.getProfile());
        notification2.setId(notificationRepository.save(notification2).getId());
        Notification beforeUpdate = Notification.NotificationBuilder.aNotification()
            .withEvent(notification2.getEvent())
            .withFavorite(notification2.getFavorite())
            .withMessage(notification2.getMessage())
            .withRecipient(notification2.getRecipient())
            .withSeen(notification2.isSeen())
            .withSender(notification2.getSender())
            .withTask(notification2.getTask())
            .withType(notification2.getType())
            .withId(notification2.getId())
            .build();
        notification2.setSeen(true);
        String body = objectMapper.writeValueAsString(notificationMapper.notificationToSimpleNotificationDto(notification2));
        MvcResult mvcResult = this.mockMvc.perform(put(NOTIFICATION_BASE_URI + "/" + beforeUpdate.getId())
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(EMPLOYEE_EMAIL, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertNotEquals(beforeUpdate, notificationRepository.findById(beforeUpdate.getId()).get()),
//            () -> assertNotEquals(beforeUpdate, notificationMapper.simpleNotificationDtoToNotification(afterUpdate)),
            () -> assertEquals(HttpStatus.OK.value(), response.getStatus())
        );
    }

}
