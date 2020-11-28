package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RegisterEmployerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.RegisterEmployerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
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
public class RegisterEmployerMapperTest implements TestData {
    @Autowired
    private RegisterEmployerMapper registerEmployerMapper;

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
        .withDescription(EMPLOYER_COMPANY_DESCRIPTION)
        .withCompanyName(EMPLOYER_COMPANY_NAME)
        .build();

    @Test
    public void mapEmployerToEmployerDto_SameValueCheck(){
        RegisterEmployerDto registerEmployerDto = registerEmployerMapper.employerToRegisterEmployerDto(employer);
        assertAll(
            () -> assertEquals(EMPLOYER_EMAIL, registerEmployerDto.getEmail()),
            () -> assertEquals(EMPLOYER_FIRST_NAME, registerEmployerDto.getFirstName()),
            () -> assertEquals(EMPLOYER_LAST_NAME, registerEmployerDto.getLastName()),
            () -> assertEquals(EMPLOYER_PASSWORD, registerEmployerDto.getPassword() ),
            () -> assertEquals(EMPLOYER_PUBLIC_INFO, registerEmployerDto.getPublicInfo()),
            () -> assertEquals(EMPLOYER_COMPANY_DESCRIPTION, registerEmployerDto.getCompanyDescription()),
            () -> assertEquals(EMPLOYER_COMPANY_NAME, registerEmployerDto.getCompanyName())
        );
    }

    @Test
    public void mapEmployerDtoToEmployer_SameValueCheck(){
        RegisterEmployerDto registerEmployerDto = registerEmployerMapper.employerToRegisterEmployerDto(employer);
        Employer emp = registerEmployerMapper.employerDtoToEmployer(registerEmployerDto);
        assertAll(
            () -> assertEquals(EMPLOYEE_EMAIL, emp.getProfile().getEmail()),
            () -> assertEquals(EMPLOYEE_FIRST_NAME, emp.getProfile().getFirstName()),
            () -> assertEquals(EMPLOYEE_LAST_NAME, emp.getProfile().getLastName()),
            () -> assertEquals(EMPLOYEE_PASSWORD, emp.getProfile().getPassword() ),
            () -> assertEquals(EMPLOYEE_PUBLIC_INFO, emp.getProfile().getPublicInfo()),
            () -> assertEquals(EMPLOYER_COMPANY_DESCRIPTION, emp.getDescription()),
            () -> assertEquals(EMPLOYER_COMPANY_NAME, emp.getCompanyName())
        );
    }
}
