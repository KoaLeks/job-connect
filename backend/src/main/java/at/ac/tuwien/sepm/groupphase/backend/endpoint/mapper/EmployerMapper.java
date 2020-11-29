package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EmployerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper {
    private final ProfileMapper profileMapper;

    @Autowired
    public EmployerMapper(ProfileMapper profileMapper) {
        this.profileMapper = profileMapper;
    }

    public Employer employerDtoToEmployer(EmployerDto employerDto){
        var emp_builder = Employer.EmployerBuilder.aEmployer();
        Profile profile = profileMapper.profileDtoToProfile(employerDto.getProfileDto());
        profile.setEmployer(true);
        emp_builder.withProfile(profile);
        emp_builder.withCompanyName(employerDto.getCompanyName());
        emp_builder.withDescription(employerDto.getDescription());
        return emp_builder.build();
    }

    public EmployerDto employerToEmployerDto(Employer employer) {
        var empDto_builder = EmployerDto.EmployerDtoBuilder.aEmployerDto();
        ProfileDto profileDto = profileMapper.profileToProfileDto(employer.getProfile());
        empDto_builder.withProfileDto(profileDto);
        empDto_builder.withCompanyName(employer.getCompanyName());
        empDto_builder.withDescription(employer.getDescription());
        return empDto_builder.build();
    }
}
