package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotDeletedException;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private final MailService mailService;
    private final ContactMessageMapper contactMessageMapper;

    private final TokenService tokenService;

    @Autowired
    public ProfileEndpoint(ProfileService profileService, EmployeeService employeeService, RegisterEmployeeMapper registerEmployeeMapper, RegisterEmployerMapper registerEmployerMapper, EmployerService employerService, EmployerMapper employerMapper, EmployeeMapper employeeMapper, MailService mailService, ContactMessageMapper contactMessageMapper, TokenService tokenService) {
        this.profileService = profileService;
        this.employeeService = employeeService;
        this.registerEmployeeMapper = registerEmployeeMapper;
        this.employeeMapper = employeeMapper;
        this.registerEmployerMapper = registerEmployerMapper;
        this.employerService = employerService;
        this.employerMapper = employerMapper;
        this.mailService = mailService;
        this.contactMessageMapper = contactMessageMapper;
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/employee")
    @ApiOperation(value = "Register a new employee", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "http://localhost:4200")
    public Long registerEmployee(@Valid @RequestBody RegisterEmployeeDto registerEmployeeDto) {
        Employee employee = registerEmployeeMapper.employeeDtoToEmployee(registerEmployeeDto);
        return employeeService.createEmployee(employee);
    }

    @GetMapping(value = "/employee")
    @ApiOperation(value = "Get an employees profile details", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    public EmployeeDto getEmployee(@RequestHeader String authorization) {
        String email = tokenService.getEmailFromHeader(authorization);
        LOGGER.info("GET /api/v1/profiles/employee/{}", email);
        return employeeMapper.employeeToEmployeeDto(employeeService.findOneByEmail(email));
    }

    @GetMapping(value = "/employee/{id}/details")
    @ApiOperation(value = "Get an employees profile details by id", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    @CrossOrigin(origins = "http://localhost:4200")
    public SimpleEmployeeDto getEmployeeById(@PathVariable @NotNull Long id) {
        LOGGER.info("GET /api/v1/profiles/employee/{}", id);
        return employeeMapper.employeeToSimpleEmployeeDto(employeeService.findOneById(id));
    }

    @PutMapping(value = "/employee")
    @ApiOperation(value = "Update employee details", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmployee(@Valid @RequestBody EditEmployeeDto editEmployeeDto, @RequestHeader String authorization) {
        LOGGER.info("PUT /api/v1/profiles/employee body: {}", editEmployeeDto);
        Employee employee = tokenService.getEmployeeFromHeader(authorization);
        if (!employee.getId().equals(editEmployeeDto.getId())) {
            throw new AuthorizationServiceException("Keine Berechtigung für die Bearbeitung des gewünschten Accounts");
        }
        employeeService.updateEmployee(employeeMapper.editEmployeeDtoToEmployee(editEmployeeDto));
    }

    @PostMapping(value = "/employer")
    @ApiOperation(value = "Register a new employer", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "http://localhost:4200")
    public Long registerEmployer(@Valid @RequestBody RegisterEmployerDto registerEmployerDto) {
        Employer employer = registerEmployerMapper.employerDtoToEmployer(registerEmployerDto);
        return employerService.createEmployer(employer);
    }

    @GetMapping(value = "/employer/{id}/details")
    @ApiOperation(value = "Get an employers profile details by id")
    @CrossOrigin(origins = "http://localhost:4200")
    public SimpleEmployerDto getEmployerById(@PathVariable @NotNull Long id) {
        LOGGER.info("GET /api/v1/profiles/employer/{}", id);
        return employerMapper.employerToSimpleEmployerDto(employerService.findOneById(id));
    }

    @GetMapping(value = "/employer")
    @ApiOperation(value = "Get an employers profile details", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    @CrossOrigin(origins = "http://localhost:4200")
    public EmployerDto getEmployer(@RequestHeader String authorization) {
        Employer employer = tokenService.getEmployerFromHeader(authorization);
        LOGGER.info("GET /api/v1/profiles/employer/{}", employer.getProfile().getEmail());
        return employerMapper.employerToEmployerDto(employer);
    }

    @PutMapping(value = "/employer")
    @ApiOperation(value = "Update employer details", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmployer(@Valid @RequestBody EditEmployerDto editEmployerDto, @RequestHeader String authorization) {
        LOGGER.info("PUT /api/v1/profiles/employer body: {}", editEmployerDto);
        Employer employer = tokenService.getEmployerFromHeader(authorization);
        if (!employer.getId().equals(editEmployerDto.getId())) {
            throw new AuthorizationServiceException("Keine Berechtigung für die Bearbeitung des gewünschten Accounts");
        }
        employerService.updateEmployer(employerMapper.editEmployerDtoToEmployer(editEmployerDto));
    }
    @GetMapping(value = "/employers")
    @ApiOperation(value = "Get list of all employers", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    public List<SimpleEmployerDto> getAllEmployers() {
        LOGGER.info("GET api/v1/profiles/employers");
        return this.employerMapper.employersToSimpleEmployerDtos(employerService.findAll());
    }

    @PutMapping(value = "/updatePassword")
    @ApiOperation(value = "Update profile password", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@Valid @RequestBody EditPasswordDto editPasswordDto, @RequestHeader String authorization) {
        LOGGER.info("PUT /api/v1/profiles/updatePassword body: {}", editPasswordDto);
        this.profileService.checkIfValidCurrentPassword(tokenService.getEmailFromHeader(authorization), editPasswordDto.getCurrentPassword());
        Profile profileToEdit = tokenService.getProfileFromHeader(authorization);
        profileToEdit.setPassword(editPasswordDto.getNewPassword());
        this.profileService.updateProfile(profileToEdit);
    }

    @GetMapping(value = "/employees")
    @ApiOperation(value = "Get list of all employees", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<SimpleEmployeeDto> getAllEmployees() {
        LOGGER.info("GET api/v1/profiles/employees");
        return this.employeeMapper.employeesToSimpleEmployeeDtos(employeeService.findAll());
    }

    @PostMapping(value = "/contact")
    @ApiOperation(value = "Contact an employee/r", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseStatus(HttpStatus.OK)
    public void contact(@Valid @RequestBody ContactMessageDto contactMessageDto) {
        LOGGER.info("POST api/v1/profiles/contact body: {}", contactMessageDto.toString().replace("\n", ""));
        this.mailService.sendContactMail(this.contactMessageMapper.contactMessageDtoToContactMessage(contactMessageDto));
    }

    @DeleteMapping("/employer")
    @ApiOperation(value = "Delete an employer", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployer(@RequestHeader String authorization) {
        String email = tokenService.getEmailFromHeader(authorization);
        LOGGER.info("DELETE api/v1/profiles/employer {}", email);
        if (employerService.hasActiveEvents(email)) {
            throw new NotDeletedException("Profil kann nicht gelöscht werden. Es gibt noch laufende oder zukünftige Events. Sagen Sie die Events zuerst ab.");
        } else {
            employerService.deleteByEmail(email);
        }
    }

    @DeleteMapping("/employee")
    @ApiOperation(value = "Delete an employee", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@RequestHeader String authorization) {
        String email = tokenService.getEmailFromHeader(authorization);
        LOGGER.info("DELETE api/v1/profiles/employee {}", email);
        if (employeeService.hasUpcomingTasks(email)) {
            throw new NotDeletedException("Profil kann nicht gelöscht werden. Es gibt noch laufende oder zukünftige Events. Melden Sie sich zuerst ab.");
        } else {
            employeeService.deleteByEmail(email);
        }
    }

    @PostMapping(value = "/filterEmployee")
    @ApiOperation(value = "Filter Employees with given args", authorizations = {@Authorization(value = "apiKey")})
    @CrossOrigin(origins = "http://localhost:4200")
    @ResponseStatus(HttpStatus.OK)
    public void filterEmployees() {
        Set<Integer> interests = new HashSet<>();
        interests.add(1);
        this.employeeService.findEmployeeByInterestArea(interests);
    }
}
