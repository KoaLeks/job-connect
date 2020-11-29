package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EventEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private Event event = Event.EventBuilder.aEvent()
        .withStart(START)
        .withEnd(END)
        .withDescription(DESCRIPTION_EVENT)
        .withEmployer(EMPLOYER)
        .withAddress(ADDRESS)
        .withTask(TASKS_EVENT)
        .build();

    @BeforeEach
    public void beforeEach() {
        eventRepository.deleteAll();
        event = Event.EventBuilder.aEvent()
            .withStart(START)
            .withEnd(END)
            .withDescription(DESCRIPTION_EVENT)
            .withEmployer(EMPLOYER)
            .withAddress(ADDRESS)
            .withTask(TASKS_EVENT)
            .build();
    }

    @Test
    public void createValidEventTest() throws Exception {
        String body = objectMapper.writeValueAsString(eventMapper.eventToEventInquiryDto(event));

        MvcResult mvcResult = this.mockMvc.perform(post(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(eventRepository.count(), 1);
    }

    @Test
    public void tryCreateEventWithoutStart_End_Description_Employer_Address_Tasks_ShouldReturnBadRequest() throws Exception {
        event.setStart(null);
        event.setEnd(null);
        event.setDescription(null);
        event.setEmployer(null);
        event.setAddress(null);
        event.setTasks(null);

        String body = objectMapper.writeValueAsString(eventMapper.eventToEventInquiryDto(event));

        MvcResult mvcResult = this.mockMvc.perform(post(EVENTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                //Reads the errors from the body
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(6, errors.length);
            }
        );
    }


}


