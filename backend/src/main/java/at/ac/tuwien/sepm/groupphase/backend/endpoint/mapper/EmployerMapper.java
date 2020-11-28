package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EmployeeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EmployerDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
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


    public Employer employerDtoToEmployee(EmployerDto employerDto){
        var emp_builder = Employer.EmployerBuilder.aEmployer();
        Profile profile = profileMapper.profileDtoToProfile(employerDto);
        profile.setEmployer(true);
        emp_builder.withProfile(profile);
        emp_builder.withDescription(employerDto.getCompanyDescription());
        emp_builder.withCompanyName(employerDto.getCompanyName());
        return emp_builder.build();
    }
}
