package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.entity.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class EmployeeMapper {
    private final ProfileMapper profileMapper;
    private final InterestMapper interestMapper;
    private final TimeMapper timeMapper;

    @Autowired
    public EmployeeMapper(ProfileMapper profileMapper, InterestMapper interestMapper, TimeMapper timeMapper) {
        this.profileMapper = profileMapper;
        this.interestMapper = interestMapper;
        this.timeMapper = timeMapper;
    }

    public Employee employeeDtoToEmployee(EmployeeDto employeeDto) {
        var emp_builder = Employee.EmployeeBuilder.aEmployee();
        Profile profile = profileMapper.profileDtoToProfile(employeeDto.getProfileDto());
        emp_builder.withProfile(profile);
        Set<Interest> interests = interestMapper.interestDtoSetToInterestSet(employeeDto.getInterestDtos());
        emp_builder.withInterests(interests);
        emp_builder.withGender(employeeDto.getGender());
        emp_builder.withBirthDate(employeeDto.getBirthDate());
        Set<Time> times = timeMapper.toTimeSet(employeeDto.getTimes());
        emp_builder.withTimes(times);

        return emp_builder.build();
    }

    public Employee editEmployeeDtoToEmployee(EditEmployeeDto editEmployeeDto) {
        var emp_builder = Employee.EmployeeBuilder.aEmployee();
        Profile profile = profileMapper.editProfileDtoToProfile(editEmployeeDto.getEditProfileDto());
        emp_builder.withProfile(profile);
        Set<Interest> interests = interestMapper.interestDtoSetToInterestSet(editEmployeeDto.getInterestDtos());
        emp_builder.withInterests(interests);
        emp_builder.withGender(editEmployeeDto.getGender());
        emp_builder.withBirthDate(editEmployeeDto.getBirthDate());
        Set<Time> times = timeMapper.toTimeSet(editEmployeeDto.getTimes());
        emp_builder.withTimes(times);

        return emp_builder.build();
    }

    public EmployeeDto employeeToEmployeeDto(Employee employee) {
        var empDto_builder = EmployeeDto.EmployeeDtoBuilder.aEmployeeDto();
        empDto_builder.withProfileDto(profileMapper.profileToProfileDto(employee.getProfile()));
        Set<InterestDto> interestDtos = interestMapper.interestSetToInterestDtoSet(employee.getInterests());
        empDto_builder.withInterestDtos(interestDtos);
        empDto_builder.withGender(employee.getGender());
        empDto_builder.withBirthDate(employee.getBirthDate());
        Set<TimeDto> timeDtos = timeMapper.ToTimeDtoSet(employee.getTimes());
        empDto_builder.withTimes(timeDtos);
        return empDto_builder.build();
    }

    public SimpleEmployeeDto employeeToSimpleEmployeeDto(Employee employee){
        var empDto_builder = SimpleEmployeeDto.SimpleEmployeeDtoBuilder.aSimpleEmployeeDto();
        empDto_builder.withId(employee.getId());
        empDto_builder.withSimpleProfileDto(profileMapper.profileToSimpleProfileDto(employee.getProfile()));
        empDto_builder.withInterestDtos(interestMapper.interestSetToInterestDtoSet(employee.getInterests()));
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
            list.add(employeeToSimpleEmployeeDto(employee));
        }

        return list;
    }

    public List<SuperSimpleEmployeeDto> employeesToSuperSimpleEmployeeDtos(List<Employee> employees) {
        if (employees == null) {
            return null;
        }

        List<SuperSimpleEmployeeDto> list = new ArrayList<>(employees.size());
        for (Employee employee : employees) {
            list.add(employeeToSuperSimpleEmployeeDto(employee));
        }

        return list;
    }

    public SuperSimpleEmployeeDto employeeToSuperSimpleEmployeeDto(Employee employee){
        var empDto_builder = SuperSimpleEmployeeDto.SuperSimpleEmployeeDtoBuilder.aSuperSimpleEmployeeDto();
        empDto_builder.withId(employee.getId());
        empDto_builder.withSuperSimpleProfileDto(profileMapper.profileToSuperSimpleProfileDto(employee.getProfile()));
        empDto_builder.withInterestDtos(interestMapper.interestSetToInterestDtoSet(employee.getInterests()));
        empDto_builder.withBirthDate(employee.getBirthDate());
        empDto_builder.withGender(employee.getGender());
        return empDto_builder.build();
    }
}
