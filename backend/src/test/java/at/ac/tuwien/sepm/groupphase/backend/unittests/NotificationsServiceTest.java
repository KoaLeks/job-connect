package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.repository.Employee_TasksRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import at.ac.tuwien.sepm.groupphase.backend.util.NotificationType;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class NotificationsServiceTest {

    @Autowired
    PlatformTransactionManager txm;

    TransactionStatus txstatus;

    @Autowired
    ProfileService profileService;

    @Autowired
    EventService eventService;

    @Autowired
    NotificationService notificationsService;

    @Autowired
    EmployerService employerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TaskService taskService;

    @Autowired
    Employee_TasksRepository employee_tasksRepository;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @BeforeEach
    public void setupDBTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        txstatus = txm.getTransaction(def);
        assumeTrue(txstatus.isNewTransaction());
        txstatus.setRollbackOnly();
    }

    @AfterEach
    public void tearDownDBData() {
        txm.rollback(txstatus);
    }

    @Test
    public void createValidNotificationShouldNotThrowException() {
        Event event = TestData.getNewEvent();
        Employer employer = TestData.getNewEmployer();
        Employee employee = TestData.getNewEmployee();
        Profile recipient = employer.getProfile();
        Profile sender = employee.getProfile();
        event.setEmployer(employer);
        eventService.saveEvent(event);
        employerService.createEmployer(employer);
        employeeService.createEmployee(employee);
        Notification notification = TestData.getNewNotification();
        notification.setEvent(event);
        notification.setSender(sender);
        notification.setRecipient(recipient);

        assertDoesNotThrow(
            () -> notificationsService.createNotification(notification)
        );
    }

    @Test
    public void findAllNotificationsByEventIdShouldNotThrowException() {
        Event event = TestData.getNewEvent();
        Employer employer = TestData.getNewEmployer();
        Employee employee = TestData.getNewEmployee();
        Profile recipient = employer.getProfile();
        Profile sender = employee.getProfile();
        event.setEmployer(employer);
        eventService.saveEvent(event);
        Event event2 = TestData.getNewEvent();
        event2.setEmployer(employer);
        eventService.saveEvent(event2);
        employerService.createEmployer(employer);
        employeeService.createEmployee(employee);
        Notification notification = TestData.getNewNotification();
        notification.setEvent(event);
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notificationsService.createNotification(notification);
        Notification notification2 = TestData.getNewNotification();
        notification2.setEvent(event2);
        notification2.setSender(sender);
        notification2.setRecipient(recipient);
        notificationsService.createNotification(notification2);

        assertDoesNotThrow(
            () -> notificationsService.findAllApplicationsByEvent_Id(event.getId())
        );

        assertEquals(1, (notificationsService.findAllApplicationsByEvent_Id(event.getId())).size());
    }

    @Test
    public void findAllNotificationsByRecipientIdShouldNotThrowException() {
        Event event = TestData.getNewEvent();
        Employer employer = TestData.getNewEmployer();
        Employee employee = TestData.getNewEmployee();
        event.setEmployer(employer);
        eventService.saveEvent(event);
        employerService.createEmployer(employer);
        employeeService.createEmployee(employee);

        Notification notification = TestData.getNewNotification();
        notification.setEvent(event);
        notification.setSender(employee.getProfile());
        notification.setRecipient(employer.getProfile());
        notification.setType(NotificationType.NOTIFICATION.name());
        notificationsService.createNotification(notification);

        assertDoesNotThrow(
            () -> notificationsService.findAllByRecipient_Id(employer.getId())
        );

        Set<Notification> notifications;
        notifications = notificationsService.findAllByRecipient_Id(employer.getId());
        assertEquals(1, notifications.size());
    }

    @Test
    public void deleteNotificationShouldNotThrowException() {
        Event event = TestData.getNewEvent();
        Employer employer = TestData.getNewEmployer();
        Employee employee = TestData.getNewEmployee();
        Profile recipient = employer.getProfile();
        Profile sender = employee.getProfile();
        event.setEmployer(employer);
        eventService.saveEvent(event);
        employerService.createEmployer(employer);
        employeeService.createEmployee(employee);
        Notification notification = TestData.getNewNotification();
        notification.setEvent(event);
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notificationsService.createNotification(notification);
        assertEquals(1, (notificationsService.findAllApplicationsByEvent_Id(event.getId())).size());
        assertDoesNotThrow(
            () -> notificationsService.deleteNotification(notification.getId(),
                jwtTokenizer.getAuthToken(TestData.EMPLOYER_EMAIL, TestData.ADMIN_ROLES))
        );
        assertEquals(0, (notificationsService.findAllApplicationsByEvent_Id(event.getId())).size());
    }

    @Test
    public void changeFavoriteShouldToggleBooleanValueFavoriteAndNotThrowException() {
        Event event = TestData.getNewEvent();
        Employer employer = TestData.getNewEmployer();
        Employee employee = TestData.getNewEmployee();
        Profile recipient = employer.getProfile();
        Profile sender = employee.getProfile();
        event.setEmployer(employer);
        eventService.saveEvent(event);
        employerService.createEmployer(employer);
        employeeService.createEmployee(employee);
        Notification notification = TestData.getNewNotification();
        notification.setEvent(event);
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notificationsService.createNotification(notification);

        Boolean newFavorite = !notification.getFavorite();

        assertEquals(1, (notificationsService.findAllApplicationsByEvent_Id(event.getId())).size());
        assertDoesNotThrow(
            () -> notificationsService.changeFavorite(notification)
        );
        assertEquals(notification.getFavorite(), newFavorite);
    }

    @Test
    public void deleteApplicationShouldNotThrowException() {
        Event event = TestData.getNewEvent();
        Employer employer = TestData.getNewEmployer();
        Employee employee = TestData.getNewEmployee();
        Profile recipient = employer.getProfile();
        Profile sender = employee.getProfile();
        event.setEmployer(employer);
        eventService.saveEvent(event);
        employerService.createEmployer(employer);
        employeeService.createEmployee(employee);
        Notification notification = TestData.getNewNotification();
        notification.setEvent(event);
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notificationsService.createNotification(notification);
        assertEquals(1, (notificationsService.findAllApplicationsByEvent_Id(event.getId())).size());
        assertDoesNotThrow(
            () -> notificationsService.deleteNotification(notification.getId(),
                jwtTokenizer.getAuthToken(TestData.EMPLOYER_EMAIL, TestData.ADMIN_ROLES))
        );
        assertEquals(0, (notificationsService.findAllApplicationsByEvent_Id(event.getId())).size());
    }

    @Test
    public void deleteEmployeeFromTaskShouldNotThrowException() {
        Event event = TestData.getNewEvent();
        event.setStart(LocalDateTime.of(2021, 11, 13, 12, 0, 0, 0));
        Employer employer = TestData.getNewEmployer();
        Employee employee = TestData.getNewEmployee();
        Profile recipient = employer.getProfile();
        Profile sender = employee.getProfile();
        employerService.createEmployer(employer);
        employeeService.createEmployee(employee);
        event.setEmployer(employer);
        Task task = TestData.getNewTask();
        Set<Employee_Tasks> employees = new HashSet<>();
        Employee_Tasks employee_tasks = Employee_Tasks.Employee_TasksBuilder.anEmployee_Tasks()
            .withId(null)
            .withEmployee(employee)
            .withAccepted(true)
            .withTask(task)
            .build();
        employees.add(employee_tasks);
        task.setEmployees(employees);
        Set<Task> tasks = new HashSet<>();
        tasks.add(task);
        event.setTasks(tasks);
        eventService.saveEvent(event);
        Notification notification = TestData.getNewNotification();
        notification.setEvent(event);
        notification.setSender(sender);
        notification.setRecipient(recipient);
        notificationsService.createNotification(notification);
        assertEquals(1, (notificationsService.findAllApplicationsByEvent_Id(event.getId())).size());

        notification.setType(NotificationType.EVENT_ACCEPTED.name());

        assertDoesNotThrow(
            () -> notificationsService.deleteEmployeeFromTask(task.getId(),
                jwtTokenizer.getAuthToken(TestData.EMPLOYEE_EMAIL, TestData.ADMIN_ROLES)));

        assertEquals(0, (notificationsService.findAllApplicationsByEvent_Id(event.getId())).size());

    }
}
