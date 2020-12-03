package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InterestMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static at.ac.tuwien.sepm.groupphase.backend.basetest.TestData.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class InterestMappingTest {
    private final Interest interest = Interest.InterestBuilder.aInterest()
        .withId(INTEREST_ID)
        .withName(INTEREST_NAME)
        .withDescription(INTEREST_DESCRIPTION)
        .withInterestArea(INTEREST_AREA)
        .withEmployees(INTEREST_EMPLOYEES)
        .build();

    @Autowired
    private InterestMapper interestMapper;

    @Test
    public void givenNothing_whenMapInterestToInterestDto_thenEntityHasAllProperties() {
        InterestDto interestDto = interestMapper.interestToInterestDto(interest);
        assertAll(
            () -> assertEquals(INTEREST_ID, interestDto.getId()),
            () -> assertEquals(INTEREST_NAME, interestDto.getName()),
            () -> assertEquals(INTEREST_DESCRIPTION, interestDto.getDescription()));
    }
}
