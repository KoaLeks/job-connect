package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class EmployeeRepositoryTest implements TestData {
    @Autowired
    private EmployeeRepository employeeRepository;

    private Profile profile = Profile.ProfileBuilder.aProfile()
        .withEmail(EMPLOYEE_EMAIL)
        .withForename(EMPLOYEE_FIRST_NAME)
        .withName(EMPLOYEE_LAST_NAME)
        .withPassword(EMPLOYEE_PASSWORD)
        .withPublicInfo(EMPLOYEE_PUBLIC_INFO)
        .build();
    private Employee employee = Employee.EmployeeBuilder.aEmployee().withProfile(profile).build();

    @BeforeEach
    public void beforeEach(){
        employeeRepository.deleteAll();
        profile = Profile.ProfileBuilder.aProfile()
            .withEmail(EMPLOYEE_EMAIL)
            .withForename(EMPLOYEE_FIRST_NAME)
            .withName(EMPLOYEE_LAST_NAME)
            .withPassword(EMPLOYEE_PASSWORD)
            .withPublicInfo(EMPLOYEE_PUBLIC_INFO)
            .build();
        employee = Employee.EmployeeBuilder.aEmployee()
            .withProfile(profile)
            .withGender(EMPLOYEE_GENDER)
            .build();
    }

    @Test
    public void givenNothing_whenRegisterNewEmployee_thenFindListWithOneElement(){
        employeeRepository.save(employee);

        assertAll(
            () -> assertEquals(1, employeeRepository.findAll().size()),
            () -> assertNotNull(employeeRepository.findById(employee.getId()))
        );
    }

    @Test
    public void givenNothing_whenRegisterNewEmployeeAndNewEmployeeWithSameEmail_thenFindListWithOneElement(){
        employeeRepository.save(employee);
        employeeRepository.save(employee);

        assertAll(
            () -> assertEquals(1, employeeRepository.findAll().size()),
            () -> assertNotNull(employeeRepository.findById(employee.getId()))
        );
    }


}
