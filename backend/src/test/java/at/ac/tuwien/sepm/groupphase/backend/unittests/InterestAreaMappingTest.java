package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class InterestAreaMappingTest implements TestData {

    private InterestArea interestArea = InterestArea.InterestAreaBuilder.aInterest()
        .withArea(AREA)
        .withDescription(DESCRIPTION)
        .withInterests(INTERESTS)
        .withTasks(TASKS)
        .build();

}