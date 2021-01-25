package at.ac.tuwien.sepm.groupphase.backend.unittests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.PasswordsNotMatchingException;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProfileRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProfileServiceTest {

    @Autowired
    PlatformTransactionManager txm;

    TransactionStatus txstatus;

    @Autowired
    ProfileService profileService;

    @Autowired
    ProfileRepository profileRepository;

    @BeforeEach
    public void setupDBTransaction() {
        profileRepository.deleteAll();
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        txstatus = txm.getTransaction(def);
        assumeTrue(txstatus.isNewTransaction());
        txstatus.setRollbackOnly();
    }

    @AfterEach
    public void tearDownDBData() {
        txm.rollback(txstatus);
    }

    @Test
    public void createNewValidProfileShouldNotThrowException() {
        Profile profile = TestData.getNewEmployee().getProfile();
        assertDoesNotThrow(
            () -> profileService.createProfile(profile));
    }

    @Test
    public void updateExistingProfileWithNewPasswordShouldNotThrowException() {
        Profile profile = TestData.getNewEmployee().getProfile();
        profile.setId(profileService.createProfile(profile));
        profile.setPassword("secureNewPassword");
        assertDoesNotThrow(
            () -> profileService.updateProfile(profile));
    }

    @Test
    public void enteringDifferentOldPasswordShouldThrowException() {
        Profile profile = TestData.getNewEmployee().getProfile();
        profile.setId(profileService.createProfile(profile));
        String email = profile.getEmail();
        assertThrows(PasswordsNotMatchingException.class,
            () -> profileService.checkIfValidCurrentPassword(email, "differentOldPassword"));

    }

    @Test
    public void existingProfileShouldBeFoundByIdAndNotThrowException() {
        Profile profile = TestData.getNewEmployee().getProfile();
        profile.setId(profileService.createProfile(profile));
        assertDoesNotThrow(
            () -> profileService.findOneById(profile.getId()));
    }

    @Test
    public void existingProfileShouldBeFoundByEmailAndNotThrowException() {
        Profile profile = TestData.getNewEmployee().getProfile();
        profileService.createProfile(profile);
        assertDoesNotThrow(
            () -> profileService.findProfileByEmail(profile.getEmail()));
    }


    @Test
    public void existingProfileShouldNotBeFoundByEmailAndThrowException() {
        Profile profile = TestData.getNewEmployee().getProfile();
        profileService.createProfile(profile);
        assertThrows(NotFoundException.class,
            () -> profileService.findProfileByEmail("unknown@mail"));
    }

    @Test
    public void createProfileWithAlreadyExistingMailShouldThrowException() {
        Profile profile = TestData.getNewEmployee().getProfile();
        profileService.createProfile(profile);
        Profile profile2 = TestData.getNewEmployee().getProfile();
        assertThrows(UniqueConstraintException.class,
            () -> profileService.createProfile(profile2));
    }

}




