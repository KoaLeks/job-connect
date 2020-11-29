package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TaskMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaskRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TaskEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private Task task = Task.TaskBuilder.aTask()
        .withDescription(DESCRIPTION_TASK)
        .withEmployeeCount(EMPLOYEE_COUNT)
        .withPaymentHourly(PAYMENT_HOURLY)
        .withEvent(EVENT)
        .withEmployees(EMPLOYEES)
        .withInterestArea(INTEREST_AREA)
        .build();

    @BeforeEach
    public void beforeEach() {
        taskRepository.deleteAll();
        task = Task.TaskBuilder.aTask()
            .withDescription(DESCRIPTION_TASK)
            .withEmployeeCount(EMPLOYEE_COUNT)
            .withPaymentHourly(PAYMENT_HOURLY)
            .withEvent(EVENT)
            .withEmployees(EMPLOYEES)
            .withInterestArea(INTEREST_AREA)
            .build();
    }

    @Test
    public void createValidTaskTest() throws Exception {
        String body = objectMapper.writeValueAsString(taskMapper.taskToTaskInquiryDto(task));

        MvcResult mvcResult = this.mockMvc.perform(post(TASKS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(taskRepository.count(), 1);
    }

    @Test
    public void tryCreateTaskWithoutDescription_EmployeeCount_PaymentHourly_Event_ShouldReturnBadRequest() throws Exception {
        task.setDescription(null);
        task.setEmployeeCount(null);
        task.setEvent(null);
        task.setPaymentHourly(null);
        String body = objectMapper.writeValueAsString(taskMapper.taskToTaskInquiryDto(task));

        MvcResult mvcResult = this.mockMvc.perform(post(TASKS_BASE_URI)
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
                assertEquals(5, errors.length);
            }
        );
    }

    @Test
    public void updateTaskToInvalidPaymentHourly_ShouldReturnBadRequestTest() throws Exception {
        String body = objectMapper.writeValueAsString(taskMapper.taskToTaskInquiryDto(task));

        MvcResult mvcResult = this.mockMvc.perform(post(TASKS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(taskRepository.count(), 1);

        task.setId(1L);
        task.setPaymentHourly(-20.5);

        String updatedBody = objectMapper.writeValueAsString(taskMapper.taskToTaskInquiryDto(task));

        MvcResult mvcUpdatedResult = this.mockMvc.perform(put(TASKS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedBody))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse updatedResponse = mvcUpdatedResult.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), updatedResponse.getStatus());
    }

    @Test
    public void updateTaskEmployeeCountToZeroWhenEmployeesIsNull_ShouldReturnOK() throws Exception {
        String body = objectMapper.writeValueAsString(taskMapper.taskToTaskInquiryDto(task));

        MvcResult mvcResult = this.mockMvc.perform(post(TASKS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(taskRepository.count(), 1);

        task.setId(1L);
        task.setEmployeeCount(0);
        task.setEmployees(null);

        String updatedBody = objectMapper.writeValueAsString(taskMapper.taskToTaskInquiryDto(task));

        MvcResult mvcUpdatedResult = this.mockMvc.perform(put(TASKS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedBody))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse updatedResponse = mvcUpdatedResult.getResponse();

        assertEquals(HttpStatus.OK.value(), updatedResponse.getStatus());
    }


}


