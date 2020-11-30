package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProfileRepository;
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
public class ProfileRepositoryTest implements TestData {
    @Autowired
    private ProfileRepository profileRepository;

    private Profile profile = Profile.ProfileBuilder.aProfile()
        .withEmail(EMPLOYER_EMAIL)
        .withForename(EMPLOYEE_FIRST_NAME)
        .withName(EMPLOYEE_LAST_NAME)
        .withPassword(EMPLOYEE_PASSWORD)
        .withPublicInfo(EMPLOYEE_PUBLIC_INFO)
        .build();

    @BeforeEach
    public void beforeEach(){
        profileRepository.deleteAll();
        profile = Profile.ProfileBuilder.aProfile()
            .withEmail(EMPLOYER_EMAIL)
            .withForename(EMPLOYEE_FIRST_NAME)
            .withName(EMPLOYEE_LAST_NAME)
            .withPassword(EMPLOYEE_PASSWORD)
            .withPublicInfo(EMPLOYEE_PUBLIC_INFO)
            .build();
    }

    @Test
    public void givenNothing_whenRegisterNewProfile_thenFindListWithOneElement(){
        profileRepository.save(profile);

        assertAll(
            () -> assertEquals(1, profileRepository.findAll().size()),
            () -> assertNotNull(profileRepository.findById(profile.getId()))
        );
    }

    @Test
    public void givenNothing_whenRegisterNewProfileAndNewProfileWithSameEmail_thenFindListWithOneElement(){
        profileRepository.save(profile);
        profileRepository.save(profile);

        assertAll(
            () -> assertEquals(1, profileRepository.findAll().size()),
            () -> assertNotNull(profileRepository.findById(profile.getId()))
        );
    }


}
