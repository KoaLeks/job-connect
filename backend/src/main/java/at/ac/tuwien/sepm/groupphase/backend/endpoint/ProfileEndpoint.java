package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RegisterEmployeeDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RegisterEmployerDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.RegisterEmployeeMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.RegisterEmployerMapper;
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
    private final RegisterEmployeeMapper registerEmployeeMapper;
    private final RegisterEmployerMapper registerEmployerMapper;
    private final EmployerService employerService;

    @Autowired
    public ProfileEndpoint(EmployeeService employeeService, RegisterEmployeeMapper registerEmployeeMapper, RegisterEmployerMapper registerEmployerMapper, EmployerService employerService) {
        this.employeeService = employeeService;
        this.registerEmployeeMapper = registerEmployeeMapper;
        this.registerEmployerMapper = registerEmployerMapper;
        this.employerService = employerService;
    }

    @PostMapping(value = "/employee")
    @ApiOperation(value = "Register a new employee", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerEmployee(@Valid @RequestBody RegisterEmployeeDto registerEmployeeDto){
        Employee employee = registerEmployeeMapper.employeeDtoToEmployee(registerEmployeeDto);
        return employeeService.createEmployee(employee);
    }

    @PostMapping(value = "/employer")
    @ApiOperation(value = "Register a new employee", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerEmployer(@Valid @RequestBody RegisterEmployerDto registerEmployerDto){
        Employer employer = registerEmployerMapper.employerDtoToEmployer(registerEmployerDto);
        return employerService.createEmployer(employer);
    }
}
