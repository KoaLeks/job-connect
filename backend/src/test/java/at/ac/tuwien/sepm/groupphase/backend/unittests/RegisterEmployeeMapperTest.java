package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RegisterEmployeeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProfileMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.RegisterEmployeeMapper;
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
public class RegisterEmployeeMapperTest implements TestData {
    @Autowired
    private RegisterEmployeeMapper registerEmployeeMapper;

    private final Profile profile = Profile.ProfileBuilder.aProfile()
        .withPublicInfo(EMPLOYEE_PUBLIC_INFO)
        .withPassword(EMPLOYEE_PASSWORD)
        .withName(EMPLOYEE_LAST_NAME)
        .withForename(EMPLOYEE_FIRST_NAME)
        .withId(EMPLOYEE_ID)
        .withEmail(EMPLOYEE_EMAIL)
        .build();

    private final Employee registerEmployee = Employee.EmployeeBuilder.aEmployee()
        .withGender(EMPLOYEE_GENDER)
        .withProfile(profile).build();

    @Test
    public void mapEmployeeToEmployeeDto_SameValueCheck(){
        RegisterEmployeeDto registerEmployeeDto = registerEmployeeMapper.employeeToRegisterEmployeeDto(registerEmployee);
        assertAll(
            () -> assertEquals(EMPLOYEE_EMAIL, registerEmployeeDto.getEmail()),
            () -> assertEquals(EMPLOYEE_FIRST_NAME, registerEmployeeDto.getFirstName()),
            () -> assertEquals(EMPLOYEE_LAST_NAME, registerEmployeeDto.getLastName()),
            () -> assertEquals(EMPLOYEE_PASSWORD, registerEmployeeDto.getPassword() ),
            () -> assertEquals(EMPLOYEE_PUBLIC_INFO, registerEmployeeDto.getPublicInfo()),
            () -> assertEquals(EMPLOYEE_GENDER, registerEmployeeDto.getGender())
        );
    }

    @Test
    public void mapEmployeeDtoToEmployee_SameValueCheck(){
        RegisterEmployeeDto registerEmployeeDto = registerEmployeeMapper.employeeToRegisterEmployeeDto(registerEmployee);
        Employee emp = registerEmployeeMapper.employeeDtoToEmployee(registerEmployeeDto);
        assertAll(
            () -> assertEquals(EMPLOYEE_EMAIL, emp.getProfile().getEmail()),
            () -> assertEquals(EMPLOYEE_FIRST_NAME, emp.getProfile().getFirstName()),
            () -> assertEquals(EMPLOYEE_LAST_NAME, emp.getProfile().getLastName()),
            () -> assertEquals(EMPLOYEE_PASSWORD, emp.getProfile().getPassword() ),
            () -> assertEquals(EMPLOYEE_PUBLIC_INFO, emp.getProfile().getPublicInfo()),
            () -> assertEquals(EMPLOYEE_GENDER, emp.getGender())
        );
    }
}
