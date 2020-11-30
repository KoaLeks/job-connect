package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
// This test slice annotation is used instead of @SpringBootTest to load only repository beans instead of
// the entire application context
@DataJpaTest
@ActiveProfiles("test")
public class EventRepositoryTest implements TestData {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void givenNothing_whenSaveMessage_thenFindListWithOneElementAndFindMessageById() {
        Event event = Event.EventBuilder.aEvent()
            .withStart(START)
            .withEnd(END)
            .withDescription(DESCRIPTION_EVENT)
            .withEmployer(EMPLOYER)
            .withAddress(ADDRESS)
            .withTask(TASKS_EVENT)
            .build();

        eventRepository.save(event);

        assertAll(
            () -> assertEquals(1, eventRepository.findAll().size()),
            () -> assertNotNull(eventRepository.findById(event.getId()))
        );
    }

}