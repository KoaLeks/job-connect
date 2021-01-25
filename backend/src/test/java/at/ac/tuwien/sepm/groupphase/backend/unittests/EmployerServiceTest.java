package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.PasswordsNotMatchingException;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmployerServiceTest {


    @Autowired
    PlatformTransactionManager txm;

    TransactionStatus txstatus;

    @Autowired
    ProfileService profileService;
    @Autowired
    EmployerService employerService;
    @Autowired
    EventService eventService;

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
    public void createNewValidEmployerShouldNotThrowException() {
        Employer employer = TestData.getNewEmployer();
        assertDoesNotThrow(
            () -> employerService.createEmployer(employer));
    }

    @Test
    public void updateExistingEmployerWithNewCompanyNameShouldNotThrowException() {
        Employer employer = TestData.getNewEmployer();
        employerService.createEmployer(employer);
        String compName = "new Comp GmbH";
        employer.setCompanyName(compName);
        assertDoesNotThrow(
            () -> employerService.updateEmployer(employer));
    }

    @Test
    public void findSavedEmployerByIdShouldNotThrowException() {
        Employer employer = TestData.getNewEmployer();
        employerService.createEmployer(employer);
        assertEquals(employer, employerService.findOneById(employer.getId()));
        assertDoesNotThrow(
            () -> employerService.findOneById(employer.getId()));
    }

    @Test
    public void findNotSavedEmployerByMailShouldThrowException() {
        Employer employer = TestData.getNewEmployer();
        employerService.createEmployer(employer);
        assertEquals(employer, employerService.findOneByEmail(employer.getProfile().getEmail()));
        assertThrows(NotFoundException.class,
            () -> employerService.findOneByEmail("noExistingMail@mail.com"));
    }

    @Test
    public void findEmployerByEventShouldNotThrowException() {
        Event event = TestData.getNewEvent();
        eventService.saveEvent(event);
        Employer employer = TestData.getNewEmployer();
        employerService.createEmployer(employer);
        event.setEmployer(employer);
        assertDoesNotThrow(
            () -> employerService.findByEvent(event));
        assertEquals(employer, employerService.findByEvent(event));
    }

    @Test
    public void findAllEmployersShouldNotThrowException() {
        Employer employer1 = TestData.getNewEmployer();
        employerService.createEmployer(employer1);
        Employer employer2 = TestData.getNewEmployer();
        employer2.getProfile().setEmail("employer2@mail.com");
        employerService.createEmployer(employer2);
        assertDoesNotThrow(
            () -> employerService.findAll());
        assertEquals(2, (employerService.findAll()).size());
    }

    @Test
    public void countingActiveEventsOfEmployerShouldReturnBooleanAndNotThrowException() {
        // employer1 event and time in future
        Event event1 = TestData.getNewEvent();
        event1.setStart(LocalDateTime.of(2022, 11, 13, 12, 0, 0, 0));
        event1.setEnd(LocalDateTime.of(2023, 11, 13, 12, 0, 0, 0));
        Employer employer1 = TestData.getNewEmployer();
        employer1.getProfile().setEmail("employer1@mail.com");
        employerService.createEmployer(employer1);
        event1.setEmployer(employer1);
        eventService.saveEvent(event1);

        // employer2 event and time in future
        Event event2 = TestData.getNewEvent();
        event2.setStart(LocalDateTime.of(2022, 11, 13, 12, 0, 0, 0));
        event2.setEnd(LocalDateTime.of(2023, 11, 13, 12, 0, 0, 0));
        Employer employer2 = TestData.getNewEmployer();
        employer2.getProfile().setEmail("employer2@mail.com");
        employerService.createEmployer(employer2);
        event2.setEmployer(employer2);
        eventService.saveEvent(event2);

        // employer3 event and time in past
        Event event3 = TestData.getNewEvent();
        event3.setStart(LocalDateTime.of(2019, 11, 13, 12, 0, 0, 0));
        event3.setEnd(LocalDateTime.of(2020, 11, 13, 12, 0, 0, 0));
        Employer employer3 = TestData.getNewEmployer();
        employer3.getProfile().setEmail("employer3@mail.com");
        employerService.createEmployer(employer3);
        event3.setEmployer(employer3);
        eventService.saveEvent(event3);

        // employer4 no events
        Employer employer4 = TestData.getNewEmployer();
        employer4.getProfile().setEmail("employer4@mail.com");
        employerService.createEmployer(employer4);

        assertDoesNotThrow(
            () -> employerService.hasActiveEvents(employer1.getProfile().getEmail())
        );
        assertDoesNotThrow(
            () -> employerService.hasActiveEvents(employer2.getProfile().getEmail())
        );
        assertDoesNotThrow(
            () -> employerService.hasActiveEvents(employer3.getProfile().getEmail())
        );
        assertDoesNotThrow(
            () -> employerService.hasActiveEvents(employer4.getProfile().getEmail())
        );
        assertTrue(employerService.hasActiveEvents(employer1.getProfile().getEmail()));
        assertTrue(employerService.hasActiveEvents(employer2.getProfile().getEmail()));
        assertFalse(employerService.hasActiveEvents(employer3.getProfile().getEmail()));
        assertFalse(employerService.hasActiveEvents(employer4.getProfile().getEmail()));

    }

    @Test
    public void deleteEmployerByEmailShouldNotThrowException() {
        Employer employer = TestData.getNewEmployer();
        employerService.createEmployer(employer);
        assertEquals(1, (employerService.findAll()).size());
        assertEquals(employer, employerService.findOneByEmail(employer.getProfile().getEmail()));
        assertDoesNotThrow(
            () -> employerService.deleteByEmail(employer.getProfile().getEmail()));
        assertEquals(0, (employerService.findAll()).size());
    }


}




