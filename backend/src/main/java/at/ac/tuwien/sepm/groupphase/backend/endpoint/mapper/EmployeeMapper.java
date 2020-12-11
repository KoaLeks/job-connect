package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
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

    public Employee employeeDtoToEmployee(EmployeeDto employeeDto) {
        var emp_builder = Employee.EmployeeBuilder.aEmployee();
        Profile profile = profileMapper.profileDtoToProfile(employeeDto.getProfileDto());
        emp_builder.withProfile(profile);
        Set<Interest> interests = interestMapper.interestDtoToInterest(employeeDto.getInterestDtos());
        emp_builder.withInterests(interests);
        emp_builder.withGender(employeeDto.getGender());
        emp_builder.withBirthDate(employeeDto.getBirthDate());

        return emp_builder.build();
    }

    public Employee editEmployeeDtoToEmployee(EditEmployeeDto editEmployeeDto) {
        var emp_builder = Employee.EmployeeBuilder.aEmployee();
        Profile profile = profileMapper.editProfileDtoToProfile(editEmployeeDto.getEditProfileDto());
        emp_builder.withProfile(profile);
        Set<Interest> interests = interestMapper.interestDtoToInterest(editEmployeeDto.getInterestDtos());
        emp_builder.withInterests(interests);
        emp_builder.withGender(editEmployeeDto.getGender());
        emp_builder.withBirthDate(editEmployeeDto.getBirthDate());

        return emp_builder.build();
    }

    public EmployeeDto employeeToEmployeeDto(Employee employee) {
        var empDto_builder = EmployeeDto.EmployeeDtoBuilder.aEmployeeDto();
        empDto_builder.withProfileDto(profileMapper.profileToProfileDto(employee.getProfile()));
        Set<InterestDto> interestDtos = interestMapper.interestToInterestDto(employee.getInterests());
        empDto_builder.withInterestDtos(interestDtos);
        empDto_builder.withGender(employee.getGender());
        empDto_builder.withBirthDate(employee.getBirthDate());
        return empDto_builder.build();
    }

    public SimpleEmployeeDto employeeToSimpleEmployeeDto(Employee employee){
        var empDto_builder = SimpleEmployeeDto.SimpleEmployeeDtoBuilder.aSimpleEmployeeDto();
        empDto_builder.withId(employee.getId());
        empDto_builder.withSimpleProfileDto(profileMapper.profileToSimpleProfileDto(employee.getProfile()));
        empDto_builder.withInterests(employee.getInterests());
        empDto_builder.withBirthDate(employee.getBirthDate());
        empDto_builder.withGender(employee.getGender());
        return empDto_builder.build();
    }

    public SimpleEmployeeDto employeeToSimpleEmployeeDto_WithoutInterests(Employee employee){
        var empDto_builder = SimpleEmployeeDto.SimpleEmployeeDtoBuilder.aSimpleEmployeeDto();
        empDto_builder.withId(employee.getId());
        empDto_builder.withSimpleProfileDto(profileMapper.profileToSimpleProfileDto(employee.getProfile()));
        empDto_builder.withBirthDate(employee.getBirthDate());
        empDto_builder.withGender(employee.getGender());
        return empDto_builder.build();
    }

    public List<SimpleEmployeeDto> employeesToSimpleEmployeeDtos(List<Employee> employees) {
        if (employees == null) {
            return null;
        }

        List<SimpleEmployeeDto> list = new ArrayList<>(employees.size());
        for (Employee employee : employees) {
            list.add(employeeToSimpleEmployeeDto_WithoutInterests(employee));
        }

        return list;
    }
}
