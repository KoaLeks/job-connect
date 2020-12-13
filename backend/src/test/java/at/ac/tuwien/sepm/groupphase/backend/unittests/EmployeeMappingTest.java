package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EmployeeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EmployeeMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
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
public class EmployeeMappingTest implements TestData {

    @Autowired
    private EmployeeMapper employeeMapper;

    private final Profile profile = Profile.ProfileBuilder.aProfile()
        .withPublicInfo(EMPLOYEE_PUBLIC_INFO)
        .withPassword(EMPLOYEE_PASSWORD)
        .withName(EMPLOYEE_LAST_NAME)
        .withForename(EMPLOYEE_FIRST_NAME)
        .withId(EMPLOYEE_ID)
        .withEmail(EMPLOYEE_EMAIL)
        .build();

    private final Employee employee = Employee.EmployeeBuilder.aEmployee()
        .withProfile(profile)
        .withGender(EMPLOYEE_GENDER)
        .withBirthDate(EMPLOYEE_BIRTH_DATE)
        .build();

    private final ProfileDto profileDto = ProfileDto.ProfileDtoBuilder.aProfileDto()
        .withPublicInfo(EMPLOYEE_PUBLIC_INFO)
        .withPassword(EMPLOYEE_PASSWORD)
        .withLastName(EMPLOYEE_LAST_NAME)
        .withFirstName(EMPLOYEE_FIRST_NAME)
        .withEmail(EMPLOYEE_EMAIL)
        .build();

    private final EmployeeDto employeeDto = EmployeeDto.EmployeeDtoBuilder.aEmployeeDto()
        .withProfileDto(profileDto)
        .withGender(EMPLOYEE_GENDER)
        .withBirthDate(EMPLOYEE_BIRTH_DATE)
        .build();

    @Test
    public void givenNothing_whenMapEmployeeToEmployeeDto_thenEntityHasAllProperties() {
        EmployeeDto employeeDto = employeeMapper.employeeToEmployeeDto(employee);
        assertAll(
            () -> assertEquals(EMPLOYEE_PUBLIC_INFO, employeeDto.getProfileDto().getPublicInfo()),
            () -> assertEquals(EMPLOYEE_PASSWORD, employeeDto.getProfileDto().getPassword()),
            () -> assertEquals(EMPLOYEE_LAST_NAME, employeeDto.getProfileDto().getLastName()),
            () -> assertEquals(EMPLOYEE_FIRST_NAME, employeeDto.getProfileDto().getFirstName()),
            () -> assertEquals(EMPLOYEE_EMAIL, employeeDto.getProfileDto().getEmail()),
            () -> assertEquals(EMPLOYEE_GENDER, employeeDto.getGender()),
            () -> assertEquals(EMPLOYEE_BIRTH_DATE, employeeDto.getBirthDate()),
            () -> assertEquals(EMPLOYEE_TIMESET, employeeDto.getTimes())
        );
    }

    @Test
    public void givenNothing_whenMapEmployeeDtoToEmployee_thenEntityHasAllProperties() {
        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);
        assertAll(
            () -> assertEquals(EMPLOYEE_PUBLIC_INFO, employee.getProfile().getPublicInfo()),
            () -> assertEquals(EMPLOYEE_PASSWORD, employee.getProfile().getPassword()),
            () -> assertEquals(EMPLOYEE_LAST_NAME, employee.getProfile().getLastName()),
            () -> assertEquals(EMPLOYEE_FIRST_NAME, employee.getProfile().getFirstName()),
            () -> assertEquals(EMPLOYEE_EMAIL, employee.getProfile().getEmail()),
            () -> assertEquals(EMPLOYEE_GENDER, employeeDto.getGender()),
            () -> assertEquals(EMPLOYEE_BIRTH_DATE, employeeDto.getBirthDate()),
            () -> assertEquals(EMPLOYEE_TIMESET, employeeDto.getTimes())
        );
    }
}
