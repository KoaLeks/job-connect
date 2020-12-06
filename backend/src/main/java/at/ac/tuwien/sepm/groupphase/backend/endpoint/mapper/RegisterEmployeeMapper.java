package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RegisterEmployeeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RegisterEmployerDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterEmployeeMapper {
    private final ProfileMapper profileMapper;

    @Autowired
    public RegisterEmployeeMapper(ProfileMapper profileMapper) {
        this.profileMapper = profileMapper;
    }


    public Employee employeeDtoToEmployee(RegisterEmployeeDto registerEmployeeDto){
        var emp_builder = Employee.EmployeeBuilder.aEmployee();
        Profile profile = profileMapper.profileDtoToProfile(registerEmployeeDto);
        emp_builder.withProfile(profile);
        emp_builder.withGender(registerEmployeeDto.getGender());
        emp_builder.withBirthDate(registerEmployeeDto.getBirthDate());
        return emp_builder.build();
    }

    public RegisterEmployeeDto employeeToRegisterEmployeeDto(Employee employee){
        var emp = profileMapper.profileToProfileDto(employee.getProfile());
        RegisterEmployeeDto registerEmployeeDto = new RegisterEmployeeDto();
        registerEmployeeDto.setEmail(emp.getEmail());
        registerEmployeeDto.setFirstName(emp.getFirstName());
        registerEmployeeDto.setLastName(emp.getLastName());
        registerEmployeeDto.setPublicInfo(emp.getPublicInfo());
        registerEmployeeDto.setPassword(emp.getPassword());
        registerEmployeeDto.setGender(employee.getGender());
        registerEmployeeDto.setPicture(emp.getPicture());
        registerEmployeeDto.setBirthDate(employee.getBirthDate());
        return registerEmployeeDto;
    }
}
