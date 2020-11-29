package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestAreaRepository;
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
public class InterestAreaRepositoryTest implements TestData {

    @Autowired
    private InterestAreaRepository interestAreaRepository;

    @Test
    public void givenNothing_whenSaveMessage_thenFindListWithOneElementAndFindMessageById() {
        InterestArea interestArea = InterestArea.InterestAreaBuilder.aInterest()
            .withArea(AREA)
            .withDescription(DESCRIPTION)
            .withInterests(INTERESTS)
            .withTasks(TASKS)
            .build();

        interestAreaRepository.save(interestArea);

        assertAll(
            () -> assertEquals(1, interestAreaRepository.findAll().size()),
            () -> assertNotNull(interestAreaRepository.findById(interestArea.getId()))
        );
    }

}