package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

}