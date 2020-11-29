package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployeeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployerRepository;
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
public class EmployerRepositoryTest implements TestData {
    @Autowired
    private EmployerRepository employerRepository;

    private Profile profile = Profile.ProfileBuilder.aProfile()
        .withEmail(EMPLOYER_EMAIL)
        .withForename(EMPLOYER_FIRST_NAME)
        .withName(EMPLOYER_LAST_NAME)
        .withPassword(EMPLOYER_PASSWORD)
        .withPublicInfo(EMPLOYER_PUBLIC_INFO)
        .build();
    private Employer employer = Employer.EmployerBuilder.aEmployer()
        .withProfile(profile)
        .withCompanyName(EMPLOYER_COMPANY_NAME)
        .withDescription(EMPLOYER_COMPANY_DESCRIPTION)
        .build();

    @BeforeEach
    public void beforeEach(){
        employerRepository.deleteAll();
        profile = Profile.ProfileBuilder.aProfile()
            .withEmail(EMPLOYER_EMAIL)
            .withForename(EMPLOYER_FIRST_NAME)
            .withName(EMPLOYER_LAST_NAME)
            .withPassword(EMPLOYER_PASSWORD)
            .withPublicInfo(EMPLOYER_PUBLIC_INFO)
            .build();
        employer = Employer.EmployerBuilder.aEmployer()
            .withProfile(profile)
            .withCompanyName(EMPLOYER_COMPANY_NAME)
            .withDescription(EMPLOYER_COMPANY_DESCRIPTION)
            .build();
    }

    @Test
    public void givenNothing_whenRegisterNewEmployer_thenFindListWithOneElement(){
        employerRepository.save(employer);

        assertAll(
            () -> assertEquals(1, employerRepository.findAll().size()),
            () -> assertNotNull(employerRepository.findById(employer.getId()))
        );
    }

    @Test
    public void givenNothing_whenRegisterNewEmployerAndNewEmployerWithSameEmail_thenFindListWithOneElement(){
        employerRepository.save(employer);
        employerRepository.save(employer);

        assertAll(
            () -> assertEquals(1, employerRepository.findAll().size()),
            () -> assertNotNull(employerRepository.findById(employer.getId()))
        );
    }


}
