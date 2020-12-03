package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestAreaRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)

@DataJpaTest
@ActiveProfiles("test")
public class InterestRepositoryTest implements TestData {

    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private InterestAreaRepository interestAreaRepository;

    @Test
    public void givenNothing_whenSaveInterest_thenFindListWithOneElementAndFindInterestById() {
        InterestArea interestArea = InterestArea.InterestAreaBuilder.aInterest()
            .withArea(AREA)
            .withDescription(DESCRIPTION)
            .withInterests(INTERESTS)
            .withTasks(TASKS)
            .build();

        interestAreaRepository.save(interestArea);

        Interest interest = Interest.InterestBuilder.aInterest()
            .withId(INTEREST_ID)
            .withName(INTEREST_NAME)
            .withDescription(INTEREST_DESCRIPTION)
            .withInterestArea(interestArea)
            .withEmployees(INTEREST_EMPLOYEES)
            .build();

        interestRepository.save(interest);

        assertAll(
            () -> assertEquals(1, interestRepository.findAll().size()),
            () -> assertNotNull(interestRepository.findById(interest.getId()))
        );
    }
}
