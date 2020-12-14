package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ApplicationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ApplicationStatusDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ApplicationStatusMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import at.ac.tuwien.sepm.groupphase.backend.util.NotificationType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/applications")
@Validated
public class ApplicationEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ApplicationStatusMapper applicationStatusMapper;

    private final EventService eventService;
    private final Employee_TasksService employee_tasksService;
    private final TaskService taskService;
    private final NotificationService notificationService;
    private final EmployerService employerService;
    private final EmployeeService employeeService;

    private final TokenService tokenService;

    @Autowired
    public ApplicationEndpoint(ApplicationStatusMapper applicationStatusMapper, EventService eventService, Employee_TasksService employee_tasksService, TaskService taskService, NotificationService notificationService, EmployerService employerService, EmployeeService employeeService, TokenService tokenService) {
        this.applicationStatusMapper = applicationStatusMapper;
        this.eventService = eventService;
        this.employee_tasksService = employee_tasksService;
        this.taskService = taskService;
        this.notificationService = notificationService;
        this.employerService = employerService;
        this.employeeService = employeeService;
        this.tokenService = tokenService;
    }


    @PutMapping(value = "/apply")
    @ApiOperation(value = "Handle application from employee and send Notification", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "http://localhost:4200")
    public void apply(@Valid @RequestBody ApplicationDto applicationDto, @RequestHeader String authorization){
        LOGGER.info("PUT /api/v1/applications/apply message: {}", applicationDto);
        String mail = tokenService.getEmailFromHeader(authorization);
        Employee employee = employeeService.findOneByEmail(mail);
        Task task = taskService.findOneById(applicationDto.getTask());
        Event event = eventService.findByTask(task);
        Employer employer = employerService.findByEvent(event);

        employee_tasksService.applyForTask(employee, task);
        Notification notification = new Notification();
        notification.setEvent(event);
        notification.setMessage(applicationDto.getMessage());
        notification.setRecipient(employer.getProfile());
        notification.setSender(employee.getProfile());
        notification.setSeen(false);
        notification.setTask(task);
        notification.setType(NotificationType.APPLICATION.name());
        notificationService.createNotification(notification);
    }

    @PostMapping(value = "/changeStatus")
    @ApiOperation(value = "Change status of application and send Notification", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin(origins = "http://localhost:4200")
    public void changeApplicationStatus(@Valid @RequestBody ApplicationStatusDto applicationStatusDto, @RequestHeader String authorization){
        LOGGER.info("POST /api/v1/applications/applicationStatus/ body: {}", applicationStatusDto);

        Employee_Tasks employee_tasks = applicationStatusMapper.applicationStatusDtoToEmployee_tasks(applicationStatusDto);
        String mail = tokenService.getEmailFromHeader(authorization);
        Employer employer = employerService.findOneByEmail(mail);
        Task task = taskService.findOneById(employee_tasks.getTask().getId());
        Event event = eventService.findByTask(task);
        if(!event.getEmployer().equals(employer))
        {
            throw new AuthorizationServiceException(String.format("No Authorization to modify the Event from Task: %s", task.getId()));
        }

        Notification notification = new Notification();
        notification.setEvent(event);
        Profile p = new Profile();
        p.setId(employee_tasks.getEmployee().getId());
        notification.setRecipient(p);
        notification.setSender(employer.getProfile());
        notification.setTask(employee_tasks.getTask());
        notification.setSeen(false);
        if(employee_tasks.getAccepted()){
            // add employee to task
            Task t = taskService.findOneById(employee_tasks.getTask().getId());
            Set<Employee> newEmployees = new HashSet<>();
            newEmployees.addAll(t.getEmployees());
            newEmployees.add(employee_tasks.getEmployee());
            t.setEmployees(newEmployees);
            taskService.updateTask(t);
            // add task to employee
            Employee e = employeeService.findOneById(employee_tasks.getEmployee().getId());
            Set<Employee_Tasks> newTasks = new HashSet<>();
            newTasks.addAll(e.getTasks());
            newTasks.add(employee_tasks);
            e.setTasks(newTasks);
            employeeService.updateEmployee(e);
            notification.setMessage(String.format("Your application to the Event \"%s\" has been accepted", event.getTitle()));
            notification.setType(NotificationType.EVENT_ACCEPTED.name());
        }else{
            notification.setMessage(String.format("Your application to the Event \"%s\" has been declined", event.getTitle()));
            notification.setType(NotificationType.EVENT_DECLINED.name());
        }
        employee_tasksService.updateStatus(employee_tasks);
        notificationService.deleteNotification(applicationStatusDto.getNotification(), authorization);
        notificationService.createNotification(notification);
    }
}
