package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RegisterEmployerDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterEmployerMapper {
    private final ProfileMapper profileMapper;

    @Autowired
    public RegisterEmployerMapper(ProfileMapper profileMapper) {
        this.profileMapper = profileMapper;
    }


    public Employer employerDtoToEmployer(RegisterEmployerDto registerEmployerDto){
        var emp_builder = Employer.EmployerBuilder.aEmployer();
        Profile profile = profileMapper.profileDtoToProfile(registerEmployerDto);
        profile.setEmployer(true);
        emp_builder.withProfile(profile);
        emp_builder.withDescription(registerEmployerDto.getCompanyDescription());
        emp_builder.withCompanyName(registerEmployerDto.getCompanyName());
        return emp_builder.build();
    }

    public RegisterEmployerDto employerToRegisterEmployerDto(Employer employer){
        var emp = profileMapper.profileToProfileDto(employer.getProfile());
        RegisterEmployerDto registerEmployerDto = new RegisterEmployerDto();
        registerEmployerDto.setPassword(emp.getPassword());
        registerEmployerDto.setEmail(emp.getEmail());
        registerEmployerDto.setFirstName(emp.getFirstName());
        registerEmployerDto.setLastName(emp.getLastName());
        registerEmployerDto.setPublicInfo(emp.getPublicInfo());
        registerEmployerDto.setCompanyDescription(employer.getDescription());
        registerEmployerDto.setCompanyName(employer.getCompanyName());
        return registerEmployerDto;
    }
}
