package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaskRepository;
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
public class TaskRepositoryTest implements TestData {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void givenNothing_whenSaveMessage_thenFindListWithOneElementAndFindMessageById() {
        Task task = Task.TaskBuilder.aTask()
            .withDescription(DESCRIPTION_TASK)
            .withEmployeeCount(EMPLOYEE_COUNT)
            .withPaymentHourly(PAYMENT_HOURLY)
            .withEvent(EVENT)
            .withEmployees(EMPLOYEES)
            .withInterestArea(INTEREST_AREA)
            .build();

        taskRepository.save(task);

        assertAll(
            () -> assertEquals(1, taskRepository.findAll().size()),
            () -> assertNotNull(taskRepository.findById(task.getId()))
        );
    }

}