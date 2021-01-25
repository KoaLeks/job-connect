package at.ac.tuwien.sepm.groupphase.backend.unittests;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployerService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
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
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceTest {

    @Autowired
    PlatformTransactionManager txm;

    TransactionStatus txstatus;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EventService eventService;

    @Autowired
    EmployerService employerService;

    @Autowired
    InterestRepository interestRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    ProfileRepository profileRepository;


    @BeforeEach
    public void setupDBTransaction() {
        interestRepository.deleteAll();
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
    public void creatingValidEmployee_shouldNotThrowException() {
        assertDoesNotThrow(() -> employeeService.createEmployee(TestData.getNewEmployee()));
    }

    @Test
    public void findEmployeeByEmail_shouldNotThrowException() {
        Employee employee = TestData.getNewEmployee();
        String email = employee.getProfile().getEmail();
        employeeService.createEmployee(employee);
        assertDoesNotThrow(() -> employeeService.findOneByEmail(email));
    }

    @Test
    public void findEmployeeById_shouldNotThrowException() {
        Long employeeId = employeeService.createEmployee(TestData.getNewEmployee());
        assertDoesNotThrow(() -> employeeService.findOneById(employeeId));
    }

   @Test
    public void deleteEmployeeByEmail_shouldNotThrowException() {
       Long employeeId = employeeService.createEmployee(TestData.getNewEmployee());

       Employee employee = employeeService.findOneById(employeeId);
       assertDoesNotThrow(() -> employeeService.deleteByEmail(employee.getProfile().getEmail()));
   }

    @Test
    public void deleteEmployeeByEmailAndThenFindEmployeeWithId_shouldThrowNotFoundException() {
        Long employeeId = employeeService.createEmployee(TestData.getNewEmployee());

        Employee employee = employeeService.findOneById(employeeId);
        employeeService.deleteByEmail(employee.getProfile().getEmail());
        assertThrows(NotFoundException.class,
            () -> employeeService.findOneById(employeeId));
    }

    @Test
    public void deleteTimeBecauseOfTask_shouldNotThrowException() {
        Long employeeId = employeeService.createEmployee(TestData.getNewEmployee());
        Time time = TestData.getNewTime();
        Set<Time> times = new HashSet<>();
        times.add(time);
        Employee employee = employeeService.findOneById(employeeId);
        employee.setTimes(times);
        employeeService.updateEmployee(employee);
        Employee updatedEmployee = employeeService.findOneById(employeeId);

        Event event = TestData.getNewEvent();
        Task task = TestData.getNewTask();
        Employer employer = event.getEmployer();
        Long employerId = employerService.createEmployer(employer);

        event.setEmployer(employerService.findOneById(employerId));
        Set<Task> tasks = new HashSet<>();
        tasks.add(task);
        event.setTasks(tasks);
        Event savedEvent = eventService.saveEvent(event);
        Task savedTask = savedEvent.getTasks().stream().findFirst().get();

        assertDoesNotThrow(() -> employeeService.deleteTime(employeeId, savedTask.getId()));
    }

}
