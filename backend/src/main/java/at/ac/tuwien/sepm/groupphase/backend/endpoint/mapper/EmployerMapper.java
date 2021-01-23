package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployerMapper {
    private final ProfileMapper profileMapper;

    @Autowired
    public EmployerMapper(ProfileMapper profileMapper) {
        this.profileMapper = profileMapper;
    }

    public Employer employerDtoToEmployer(EmployerDto employerDto){
        if (employerDto != null) {
            var emp_builder = Employer.EmployerBuilder.aEmployer();
            Profile profile = profileMapper.profileDtoToProfile(employerDto.getProfileDto());
            profile.setEmployer(true);
            emp_builder.withProfile(profile);
            emp_builder.withCompanyName(employerDto.getCompanyName());
            emp_builder.withDescription(employerDto.getDescription());
            return emp_builder.build();
        } else {
            return null;
        }
    }

    public Employer editEmployerDtoToEmployer(EditEmployerDto editEmployerDto) {
        if (editEmployerDto != null) {
            var emp_builder = Employer.EmployerBuilder.aEmployer();
            Profile profile = profileMapper.editProfileDtoToProfile(editEmployerDto.getEditProfileDto());
            profile.setEmployer(true);
            emp_builder.withProfile(profile);
            emp_builder.withCompanyName(editEmployerDto.getCompanyName());
            emp_builder.withDescription(editEmployerDto.getDescription());
            return emp_builder.build();
        } else {
            return null;
        }
    }

    public EmployerDto employerToEmployerDto(Employer employer) {
        if (employer != null) {
            var empDto_builder = EmployerDto.EmployerDtoBuilder.aEmployerDto();
            ProfileDto profileDto = profileMapper.profileToProfileDto(employer.getProfile());
            empDto_builder.withProfileDto(profileDto);
            empDto_builder.withCompanyName(employer.getCompanyName());
            empDto_builder.withDescription(employer.getDescription());
            return empDto_builder.build();
        } else {
            return null;
        }
    }

    public Employer simpleEmployerDtoToEmployer(SimpleEmployerDto simpleEmployerDto) {
        if (simpleEmployerDto != null) {
            var emp_builder = Employer.EmployerBuilder.aEmployer();
            Profile profile = profileMapper.simpleProfileDtoToProfile(simpleEmployerDto.getSimpleProfileDto());
            profile.setEmployer(true);
            emp_builder
                .withProfile(profile)
                .withCompanyName(simpleEmployerDto.getCompanyName())
                .withDescription(simpleEmployerDto.getCompanyDescription());
            return emp_builder.build();
        } else {
            return null;
        }
    }
    public SimpleEmployerDto employerToSimpleEmployerDto(Employer employer) {
        if (employer != null) {
            var empDto_builder = SimpleEmployerDto.SimpleEmployerDtoBuilder.aSimpleEmployerDto();
            SimpleProfileDto profile = profileMapper.profileToSimpleProfileDto(employer.getProfile());
            empDto_builder
                .withProfileDto(profile)
                .withCompanyName(employer.getCompanyName())
                .withCompanyDescription(employer.getDescription());
            return empDto_builder.build();
        } else {
            return null;
        }
    }

    public List<SimpleEmployerDto> employersToSimpleEmployerDtos(List<Employer> employers){
        List<SimpleEmployerDto> simpleEmployerList = new ArrayList<>();
        for (Employer employer:
            employers) {
            simpleEmployerList.add(employerToSimpleEmployerDto(employer));
        }
        return simpleEmployerList;
    }

    public List<SuperSimpleEmployerDto> employersToSuperSimpleEmployerDtos(List<Employer> employers){
        List<SuperSimpleEmployerDto> simpleEmployerList = new ArrayList<>();
        for (Employer employer:
            employers) {
            simpleEmployerList.add(employerToSuperSimpleEmployerDto(employer));
        }
        return simpleEmployerList;
    }

    public SuperSimpleEmployerDto employerToSuperSimpleEmployerDto(Employer employer) {
        if (employer != null) {
            var empDto_builder = SuperSimpleEmployerDto.SuperSimpleEmployerDtoBuilder.aSuperSimpleEmployerDto();
            SuperSimpleProfileDto profile = profileMapper.profileToSuperSimpleProfileDto(employer.getProfile());
            empDto_builder
                .withSuperSimpleProfileDto(profile)
                .withCompanyName(employer.getCompanyName())
                .withCompanyDescription(employer.getDescription());
            return empDto_builder.build();
        } else {
            return null;
        }
    }
}
