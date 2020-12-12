package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EditProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import org.mapstruct.Mapper;

@Mapper
public interface ProfileMapper {
    Profile profileDtoToProfile(ProfileDto profileDto);
    ProfileDto profileToProfileDto(Profile profile);
    Profile editProfileDtoToProfile(EditProfileDto editProfileDto);
    SimpleProfileDto profileToSimpleProfileDto(Profile profile);
    Profile simpleProfileDtoToProfile(SimpleProfileDto profile);
}
