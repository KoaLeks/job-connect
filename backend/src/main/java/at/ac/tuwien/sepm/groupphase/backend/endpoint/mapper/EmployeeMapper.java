package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EmployeeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    private final ProfileMapper profileMapper;

    @Autowired
    public EmployeeMapper(ProfileMapper profileMapper) {
        this.profileMapper = profileMapper;
    }


    public Employee employeeDtoToEmployee(EmployeeDto employeeDto){
        var emp_builder = Employee.EmployeeBuilder.aEmployee();
        Profile profile = profileMapper.profileDtoToProfile(employeeDto);
        emp_builder.withProfile(profile);
        return emp_builder.build();
    }
}
