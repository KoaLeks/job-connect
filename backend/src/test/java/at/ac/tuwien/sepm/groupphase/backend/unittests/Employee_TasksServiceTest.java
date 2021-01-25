package at.ac.tuwien.sepm.groupphase.backend.unittests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.AlreadyHandledException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NoAvailableSpacesException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.Employee_TasksService;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployerService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class Employee_TasksServiceTest {

    @Autowired
    PlatformTransactionManager txm;

    TransactionStatus txstatus;

    @Autowired
    Employee_TasksService employee_tasksService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EventService eventService;

    @Autowired
    EmployerService employerService;

    @Autowired
    Employee_TasksRepository employee_tasksRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ProfileRepository profileRepository;

    @BeforeEach
    public void setupDBTransaction() {
        employee_tasksRepository.deleteAll();
        eventRepository.deleteAll();
        employerRepository.deleteAll();
        employeeRepository.deleteAll();
        profileRepository.deleteAll();
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
    public void applyForTaskFirstTime_shouldNotThrowException() {
        Employee employee = TestData.getNewEmployee();
        employee.setId(employeeService.createEmployee(TestData.getNewEmployee()));
        Event event = TestData.getNewEvent();
        Set<Task> tasks = new HashSet<>();
        tasks.add(TestData.getNewTask());
        event.setTasks(tasks);

        event.setEmployer(employerService.findOneById(employerService.createEmployer(TestData.getNewEmployer())));
        tasks = eventService.saveEvent(event).getTasks();
        Task task = tasks.stream().findFirst().isPresent() ? tasks.stream().findFirst().get() : null;

        assertDoesNotThrow(() -> employee_tasksService.applyForTask(employee, task));
    }

    @Test
    public void applyForTaskTwice_shouldThrowAlreadyHandledException() {
        Employee employee = TestData.getNewEmployee();
        employee.setId(employeeService.createEmployee(TestData.getNewEmployee()));
        Event event = TestData.getNewEvent();
        Set<Task> tasks = new HashSet<>();
        Task task = TestData.getNewTask();
        task.setEmployeeCount(1);
        tasks.add(task);
        event.setTasks(tasks);

        event.setEmployer(employerService.findOneById(employerService.createEmployer(TestData.getNewEmployer())));
        Event savedEvent =  eventService.saveEvent(event);
        Task savedTask = savedEvent.getTasks().stream().findFirst().get();

        employee_tasksService.applyForTask(employee, savedTask);

        assertThrows(AlreadyHandledException.class,
            () -> employee_tasksService.applyForTask(employee, savedTask));
    }

    @Test
    public void applyForFullTask_shouldThrowNoAvailableSpacesException() {
        Employee employee = TestData.getNewEmployee();
        employee.setId(employeeService.createEmployee(TestData.getNewEmployee()));
        Event event = TestData.getNewEvent();
        Set<Task> tasks = new HashSet<>();
        Task task = TestData.getNewTask();
        task.setEmployeeCount(1);
        tasks.add(task);
        event.setTasks(tasks);

        event.setEmployer(employerService.findOneById(employerService.createEmployer(TestData.getNewEmployer())));
        Event savedEvent =  eventService.saveEvent(event);
        Task savedTask = savedEvent.getTasks().stream().findFirst().get();

        Employee_Tasks employee_task = employee_tasksRepository.getOne(employee_tasksService.applyForTask(employee, savedTask));

        employee_task.setAccepted(true);

        Employee newEmployee = TestData.getNewEmployee();
        newEmployee.setId(null);
        newEmployee.getProfile().setEmail("newEmail@gmail.com");
        newEmployee.setId(employeeService.createEmployee(newEmployee));

        assertThrows(NoAvailableSpacesException.class,
            () -> employee_tasksService.applyForTask(newEmployee, savedTask));
    }

    @Test
    public void updateStatusOnDeletedApplication_shouldThrowNotFoundException() {
        Employee employee = TestData.getNewEmployee();
        employee.setId(employeeService.createEmployee(TestData.getNewEmployee()));
        Event event = TestData.getNewEvent();
        Set<Task> tasks = new HashSet<>();
        Task task = TestData.getNewTask();
        task.setEmployeeCount(1);
        tasks.add(task);
        event.setTasks(tasks);

        event.setEmployer(employerService.findOneById(employerService.createEmployer(TestData.getNewEmployer())));
        Event savedEvent =  eventService.saveEvent(event);
        Task savedTask = savedEvent.getTasks().stream().findFirst().get();

        Employee_Tasks employee_task = employee_tasksRepository.getOne(employee_tasksService.applyForTask(employee, savedTask));

        employee_tasksRepository.delete(employee_task);

        assertThrows(NotFoundException.class,
            () -> employee_tasksService.updateStatus(employee_task));
    }

    @Test
    public void updateStatusOnAlreadyAcceptedApplication_shouldThrowAlreadyHandledException() {
        Employee employee = TestData.getNewEmployee();
        employee.setId(employeeService.createEmployee(TestData.getNewEmployee()));
        Event event = TestData.getNewEvent();
        Set<Task> tasks = new HashSet<>();
        Task task = TestData.getNewTask();
        task.setEmployeeCount(1);
        tasks.add(task);
        event.setTasks(tasks);

        event.setEmployer(employerService.findOneById(employerService.createEmployer(TestData.getNewEmployer())));
        Event savedEvent =  eventService.saveEvent(event);
        Task savedTask = savedEvent.getTasks().stream().findFirst().get();

        Employee_Tasks employee_task = employee_tasksRepository.getOne(employee_tasksService.applyForTask(employee, savedTask));

        employee_task.setAccepted(true);

        assertThrows(AlreadyHandledException.class,
            () -> employee_tasksService.updateStatus(employee_task));
    }
}
