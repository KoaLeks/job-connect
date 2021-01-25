package at.ac.tuwien.sepm.groupphase.backend.unittests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SearchEventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployeeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployerRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProfileRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployerService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class EventServiceTest {

    @Autowired
    PlatformTransactionManager txm;

    TransactionStatus txstatus;

    @Autowired
    EventService eventService;

    @Autowired
    EmployerService employerService;

    @Autowired
    ProfileService profileService;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    EventRepository eventRepository;

    @BeforeEach
    public void setupDBTransaction() {
        eventRepository.deleteAll();
        employeeRepository.deleteAll();
        employerRepository.deleteAll();
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
    public void savingValidEvent_shouldNotThrowException() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(TestData.getNewTask());
        Event event = TestData.getNewEvent();
        event.setTasks(tasks);

        Employer employer = TestData.getNewEmployer();
        Long employerId = employerService.createEmployer(employer);
        event.setEmployer(employerService.findOneById(employerId));

        assertDoesNotThrow(() -> eventService.saveEvent(event));
    }

    @Test
    public void findAllEventsWithTitle_shouldNotThrowExceptionAndReturnAllEvents() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(TestData.getNewTask());
        Event event = TestData.getNewEvent();
        event.setTasks(tasks);

        Employer employer = TestData.getNewEmployer();
        Long employerId = employerService.createEmployer(employer);
        event.setEmployer(employerService.findOneById(employerId));
        eventService.saveEvent(event);

        Event newEvent = TestData.getNewEvent();
        newEvent.setId(null);
        newEvent.setTitle("Only This");
        newEvent.setTasks(tasks);
        newEvent.setEmployer(employerService.findOneById(employerId));
        eventService.saveEvent(newEvent);

        SearchEventDto searchEventDto = new SearchEventDto();
        searchEventDto.setTitle("Only This");

        assertDoesNotThrow(() -> eventService.findAll(searchEventDto));
        assertEquals(1, eventService.findAll(searchEventDto).size());
    }

    @Test
    public void findEventById_shouldNotThrowException() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(TestData.getNewTask());
        Event event = TestData.getNewEvent();
        event.setTasks(tasks);

        Employer employer = TestData.getNewEmployer();
        Long employerId = employerService.createEmployer(employer);
        event.setEmployer(employerService.findOneById(employerId));
        Event savedEvent = eventService.saveEvent(event);

        assertDoesNotThrow(() -> eventService.findById(savedEvent.getId()));
        assertEquals(savedEvent, eventService.findById(savedEvent.getId()));
    }

    @Test
    public void findEventByTask_shouldNotThrowException() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(TestData.getNewTask());
        Event event = TestData.getNewEvent();
        event.setTasks(tasks);

        Employer employer = TestData.getNewEmployer();
        Long employerId = employerService.createEmployer(employer);
        event.setEmployer(employerService.findOneById(employerId));
        Event savedEvent = eventService.saveEvent(event);

        Task task = savedEvent.getTasks().stream().findFirst().get();

        assertDoesNotThrow(() -> eventService.findByTask(task));
        assertEquals(savedEvent.getId(), eventService.findByTask(task).getId());
    }

    @Test
    public void deleteEventById_shouldNotThrowException() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(TestData.getNewTask());
        Event event = TestData.getNewEvent();
        event.setTasks(tasks);

        Employer employer = TestData.getNewEmployer();
        Long employerId = employerService.createEmployer(employer);
        event.setEmployer(employerService.findOneById(employerId));
        Event savedEvent = eventService.saveEvent(event);

        assertDoesNotThrow(() -> eventService.deleteEventById(savedEvent.getId()));
    }

    @Test
    public void deleteEventByIdThenFindByThatId_shouldThrowNotFoundException() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(TestData.getNewTask());
        Event event = TestData.getNewEvent();
        event.setTasks(tasks);

        Employer employer = TestData.getNewEmployer();
        Long employerId = employerService.createEmployer(employer);
        event.setEmployer(employerService.findOneById(employerId));
        Event savedEvent = eventService.saveEvent(event);

        assertDoesNotThrow(() -> eventService.deleteEventById(savedEvent.getId()));
        assertThrows(NotFoundException.class,
            () -> eventService.findById(savedEvent.getId()));
    }

    @Test
    public void findEventByEmployerId_shouldNotThrowException() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(TestData.getNewTask());
        Event event = TestData.getNewEvent();
        event.setTasks(tasks);

        Employer employer = TestData.getNewEmployer();
        Long employerId = employerService.createEmployer(employer);
        event.setEmployer(employerService.findOneById(employerId));
        Event savedEvent = eventService.saveEvent(event);

        assertDoesNotThrow(() -> eventService.findByEmployerId(employerId));
        List<Event> events = eventService.findByEmployerId(employerId);
        assertEquals(savedEvent, events.stream().findFirst().get());
    }

}
