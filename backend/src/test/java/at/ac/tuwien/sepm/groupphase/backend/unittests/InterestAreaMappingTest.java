package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestAreaDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InterestAreaMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
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
public class InterestAreaMappingTest implements TestData {

    private InterestArea interestArea = InterestArea.InterestAreaBuilder.aInterest()
        .withArea(AREA)
        .withDescription(DESCRIPTION)
        .withInterests(INTERESTS)
        .withTasks(TASKS)
        .build();

    @Autowired
    private InterestAreaMapper interestAreaMapper;

    @Test
    public void givenNothing_whenMapInterestAreaDtoToEntity_thenEntityHasAllProperties() {
        InterestAreaDto interestAreaDto = interestAreaMapper.interestAreaToInterestAreaDto(interestArea);
        assertAll(
            () -> assertEquals(AREA, interestAreaDto.getArea()),
            () -> assertEquals(DESCRIPTION, interestAreaDto.getDescription()),
            () -> assertEquals(INTERESTS, interestAreaDto.getInterests()),
            () -> assertEquals(TASKS, interestAreaDto.getTasks()));

    }
}