package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ProfileMapper;
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
public class ProfileMapperTest implements TestData {

    @Autowired
    private ProfileMapper profileMapper;

    private final Profile profile = Profile.ProfileBuilder.aProfile()
        .withPublicInfo(EMPLOYEE_PUBLIC_INFO)
        .withPassword(EMPLOYEE_PASSWORD)
        .withName(EMPLOYEE_LAST_NAME)
        .withForename(EMPLOYEE_FIRST_NAME)
        .withId(EMPLOYEE_ID)
        .withEmail(EMPLOYEE_EMAIL)
        .build();

    @Test
    public void mapProfileToProfileDto_SameValueCheck(){
        ProfileDto profileDto = profileMapper.profileToProfileDto(profile);
        assertAll(
            () -> assertEquals(EMPLOYEE_EMAIL, profileDto.getEmail()),
            () -> assertEquals(EMPLOYEE_FIRST_NAME, profileDto.getFirstName()),
            () -> assertEquals(EMPLOYEE_LAST_NAME, profileDto.getLastName()),
            () -> assertEquals(EMPLOYEE_PASSWORD, profileDto.getPassword() ),
            () -> assertEquals(EMPLOYEE_PUBLIC_INFO, profileDto.getPublicInfo())
        );
    }

    @Test
    public void mapProfileDtoToProfile_SameValueCheck(){
        ProfileDto profileDto = profileMapper.profileToProfileDto(profile);
        Profile pro = profileMapper.profileDtoToProfile(profileDto);
        assertAll(
            () -> assertEquals(EMPLOYEE_EMAIL, pro.getEmail()),
            () -> assertEquals(EMPLOYEE_FIRST_NAME, pro.getFirstName()),
            () -> assertEquals(EMPLOYEE_LAST_NAME, pro.getLastName()),
            () -> assertEquals(EMPLOYEE_PASSWORD, pro.getPassword() ),
            () -> assertEquals(EMPLOYEE_PUBLIC_INFO, pro.getPublicInfo())
        );
    }
}
