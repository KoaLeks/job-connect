package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EventMappingTest implements TestData {

    private Event event = Event.EventBuilder.aEvent()
        .withStart(START)
        .withEnd(END)
        .withDescription(DESCRIPTION_EVENT)
        .withEmployer(EMPLOYER)
        .withAddress(ADDRESS)
        .withTask(TASKS_EVENT)
        .build();

}