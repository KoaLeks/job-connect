package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EmployeeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EmployeeMapper {
    private final ProfileMapper profileMapper;
    private final InterestMapper interestMapper;

    @Autowired
    public EmployeeMapper(ProfileMapper profileMapper, InterestMapper interestMapper) {
        this.profileMapper = profileMapper;
        this.interestMapper = interestMapper;
    }

    public Employee employeeDtoToEmployee(EmployeeDto employeeDto){
        var emp_builder = Employee.EmployeeBuilder.aEmployee();
        Profile profile = profileMapper.profileDtoToProfile(employeeDto.getProfileDto());
        emp_builder.withProfile(profile);
        Set<Interest> interests = interestMapper.interestDtoToInterest(employeeDto.getInterestDtos());
        emp_builder.withInterests(interests);
        emp_builder.withGender(employeeDto.getGender());

        return emp_builder.build();
    }

    public EmployeeDto employeeToEmployeeDto(Employee employee){
        var empDto_builder = EmployeeDto.EmployeeDtoBuilder.aEmployeeDto();
        ProfileDto profileDto = profileMapper.profileToProfileDto(employee.getProfile());
        empDto_builder.withProfileDto(profileDto);
        Set<InterestDto> interestDtos = interestMapper.interestToInterestDto(employee.getInterests());
        empDto_builder.withInterestDtos(interestDtos);
        empDto_builder.withGender(employee.getGender());
        return empDto_builder.build();
    }
}
