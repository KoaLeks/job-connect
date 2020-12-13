package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/profiles")
@Validated
@Transactional
public class ProfileEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ProfileService profileService;

    private final RegisterEmployeeMapper registerEmployeeMapper;
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    private final RegisterEmployerMapper registerEmployerMapper;
    private final EmployerService employerService;
    private final EmployerMapper employerMapper;

    @Autowired
    public ProfileEndpoint(ProfileService profileService, EmployeeService employeeService, RegisterEmployeeMapper registerEmployeeMapper, RegisterEmployerMapper registerEmployerMapper, EmployerService employerService, EmployerMapper employerMapper, EmployeeMapper employeeMapper) {
        this.profileService = profileService;
        this.employeeService = employeeService;
        this.registerEmployeeMapper = registerEmployeeMapper;
        this.employeeMapper = employeeMapper;
        this.registerEmployerMapper = registerEmployerMapper;
        this.employerService = employerService;
        this.employerMapper = employerMapper;
    }

    @PostMapping(value = "/employee")
    @ApiOperation(value = "Register a new employee", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "http://localhost:4200")
    public Long registerEmployee(@Valid @RequestBody RegisterEmployeeDto registerEmployeeDto) {
        Employee employee = registerEmployeeMapper.employeeDtoToEmployee(registerEmployeeDto);
        return employeeService.createEmployee(employee);
    }

    @GetMapping(value = "/employee/{email}")
    @ApiOperation(value = "Get an employees profile details", authorizations = {@Authorization(value = "apiKey")})
    public EmployeeDto getEmployee(@PathVariable @NotNull @NotBlank String email) {
        LOGGER.info("GET /api/v1/profiles/employee/{}", email);
        return employeeMapper.employeeToEmployeeDto(employeeService.findOneByEmail(email));
    }

    @PutMapping(value = "/employee")
    @ApiOperation(value = "Update employee details", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    public Long updateEmployee(@Valid @RequestBody EditEmployeeDto editEmployeeDto) {
        LOGGER.info("PUT /api/v1/profiles/employee body: {}", editEmployeeDto);
        return employeeService.updateEmployee(employeeMapper.editEmployeeDtoToEmployee(editEmployeeDto));
    }

    @PostMapping(value = "/employer")
    @ApiOperation(value = "Register a new employer", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "http://localhost:4200")
    public Long registerEmployer(@Valid @RequestBody RegisterEmployerDto registerEmployerDto) {
        Employer employer = registerEmployerMapper.employerDtoToEmployer(registerEmployerDto);
        return employerService.createEmployer(employer);
    }

    @GetMapping(value = "/employer/{email}")
    @ApiOperation(value = "Get an employers profile details", authorizations = {@Authorization(value = "apiKey")})
    public EmployerDto getEmployer(@PathVariable @NotNull @NotBlank String email) {
        LOGGER.info("GET /api/v1/profiles/employer/{}", email);
        return employerMapper.employerToEmployerDto(employerService.findOneByEmail(email));
    }

    @PutMapping(value = "/employer")
    @ApiOperation(value = "Update employer details", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    public Long updateEmployer(@Valid @RequestBody EditEmployerDto editEmployerDto) {
        LOGGER.info("PUT /api/v1/profiles/employer body: {}", editEmployerDto);
        return employerService.updateEmployer(employerMapper.editEmployerDtoToEmployer(editEmployerDto));
    }

    @PutMapping(value = "/updatePassword")
    @ApiOperation(value = "Update profile password", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    public Long updatePassword(@Valid @RequestBody EditPasswordDto editPasswordDto) {
        LOGGER.info("PUT /api/v1/profiles/updatePassword body: {}", editPasswordDto);
        this.profileService.checkIfValidCurrentPassword(editPasswordDto.getEmail(), editPasswordDto.getCurrentPassword());

        Profile profileToEdit = this.profileService.findProfileByEmail(editPasswordDto.getEmail());
        profileToEdit.setPassword(editPasswordDto.getNewPassword());
        return this.profileService.updateProfile(profileToEdit);
    }

    @GetMapping(value = "/employee")
    @ApiOperation(value = "Get list of all employees", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    public List<SimpleEmployeeDto> getAllEmployees() {
        LOGGER.info("GET api/v1/profiles/employees");
        return this.employeeMapper.employeesToSimpleEmployeeDtos(employeeService.findAll());
    }
}
