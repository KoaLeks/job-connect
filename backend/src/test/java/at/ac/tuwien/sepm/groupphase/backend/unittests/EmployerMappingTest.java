package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EmployerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EmployerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
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
public class EmployerMappingTest {

    @Autowired
    private EmployerMapper employerMapper;

    private final Profile profile = Profile.ProfileBuilder.aProfile()
        .withPublicInfo(EMPLOYER_PUBLIC_INFO)
        .withPassword(EMPLOYER_PASSWORD)
        .withName(EMPLOYER_LAST_NAME)
        .withForename(EMPLOYER_FIRST_NAME)
        .withId(EMPLOYER_ID)
        .withEmail(EMPLOYER_EMAIL)
        .build();

    private final Employer employer = Employer.EmployerBuilder.aEmployer()
        .withProfile(profile)
        .build();

    private final ProfileDto profileDto = ProfileDto.ProfileDtoBuilder.aProfileDto()
        .withPublicInfo(EMPLOYER_PUBLIC_INFO)
        .withPassword(EMPLOYER_PASSWORD)
        .withLastName(EMPLOYER_LAST_NAME)
        .withFirstName(EMPLOYER_FIRST_NAME)
        .withEmail(EMPLOYER_EMAIL)
        .build();

    private final EmployerDto employerDto = EmployerDto.EmployerDtoBuilder.aEmployerDto()
        .withProfileDto(profileDto)
        .build();

    @Test
    public void givenNothing_whenMapEmployerToEmployerDto_thenEntityHasAllProperties() {
        EmployerDto employerDto = employerMapper.employerToEmployerDto(employer);
        assertAll(
            () -> assertEquals(EMPLOYER_PUBLIC_INFO, employerDto.getProfileDto().getPublicInfo()),
            () -> assertEquals(EMPLOYER_PASSWORD, employerDto.getProfileDto().getPassword()),
            () -> assertEquals(EMPLOYER_LAST_NAME, employerDto.getProfileDto().getLastName()),
            () -> assertEquals(EMPLOYER_FIRST_NAME, employerDto.getProfileDto().getFirstName()),
            () -> assertEquals(EMPLOYER_EMAIL, employerDto.getProfileDto().getEmail())
        );
    }

    @Test
    public void givenNothing_whenMapEmployeeDtoToEmployee_thenEntityHasAllProperties() {
        Employer employer = employerMapper.employerDtoToEmployer(employerDto);
        assertAll(
            () -> assertEquals(EMPLOYER_PUBLIC_INFO, employer.getProfile().getPublicInfo()),
            () -> assertEquals(EMPLOYER_PASSWORD, employer.getProfile().getPassword()),
            () -> assertEquals(EMPLOYER_LAST_NAME, employer.getProfile().getLastName()),
            () -> assertEquals(EMPLOYER_FIRST_NAME, employer.getProfile().getFirstName()),
            () -> assertEquals(EMPLOYER_EMAIL, employer.getProfile().getEmail())
        );
    }
}
