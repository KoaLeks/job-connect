package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TaskInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.TaskMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
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
public class TaskMappingTest implements TestData {

    private Task task = Task.TaskBuilder.aTask()
        .withDescription(DESCRIPTION_TASK)
        .withEmployeeCount(EMPLOYEE_COUNT)
        .withPaymentHourly(PAYMENT_HOURLY)
        .withEvent(EVENT)
        .withEmployees(EMPLOYEES)
        .withInterestArea(INTEREST_AREA)
        .build();

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void givenNothing_whenMapTaskInquiryDtoToEntity_thenEntityHasAllProperties() {
        TaskInquiryDto taskInquiryDto = taskMapper.taskToTaskInquiryDto(task);
        assertAll(
            () -> assertEquals(DESCRIPTION_TASK, taskInquiryDto.getDescription()),
            () -> assertEquals(EMPLOYEE_COUNT, taskInquiryDto.getEmployeeCount()),
            () -> assertEquals(PAYMENT_HOURLY, taskInquiryDto.getPaymentHourly()),
            () -> assertEquals(EVENT, taskInquiryDto.getEvent()),
            () -> assertEquals(EMPLOYEES, taskInquiryDto.getEmployees()),
            () -> assertEquals(INTEREST_AREA, taskInquiryDto.getInterestArea())
        );
    }
}