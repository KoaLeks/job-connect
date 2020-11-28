package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EmployeeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EmployerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EmployeeMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EmployerMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/profiles")
public class ProfileEndpoint {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final EmployerMapper employerMapper;
    private final EmployerService employerService;

    @Autowired
    public ProfileEndpoint(EmployeeService employeeService, EmployeeMapper employeeMapper, EmployerMapper employerMapper, EmployerService employerService) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
        this.employerMapper = employerMapper;
        this.employerService = employerService;
    }

    @PostMapping(value = "/employee")
    @ApiOperation(value = "Register a new employee", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);
        return employeeService.createEmployee(employee);
    }

    @PostMapping(value = "/employer")
    @ApiOperation(value = "Register a new employee", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerEmployer(@Valid @RequestBody EmployerDto employerDto){
        Employer employer = employerMapper.employerDtoToEmployee(employerDto);
        return employerService.createEmployer(employer);
    }
}
