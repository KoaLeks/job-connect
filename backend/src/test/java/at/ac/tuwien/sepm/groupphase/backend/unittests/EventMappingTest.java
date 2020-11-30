package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestAreaDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InterestAreaMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Autowired
    private EventMapper eventMapper;

    @Test
    public void givenNothing_whenMapEventInquiryDtoToEntity_thenEntityHasAllProperties() {
        EventInquiryDto eventInquiryDto = eventMapper.eventToEventInquiryDto(event);
        assertAll(
            () -> assertEquals(START, eventInquiryDto.getStart()),
            () -> assertEquals(END, eventInquiryDto.getEnd()),
            () -> assertEquals(DESCRIPTION_EVENT, eventInquiryDto.getDescription()),
            () -> assertEquals(ADDRESS, eventInquiryDto.getAddress()));

    }

}