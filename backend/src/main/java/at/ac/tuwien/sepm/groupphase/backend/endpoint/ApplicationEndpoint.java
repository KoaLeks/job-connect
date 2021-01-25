package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.*;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ApplicationStatusMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NotificationMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.AlreadyHandledException;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import at.ac.tuwien.sepm.groupphase.backend.util.NotificationType;
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
import java.lang.invoke.MethodHandles;
import java.util.List;
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
    private final NotificationMapper notificationMapper;
    private final EventMapper eventMapper;


    private final TokenService tokenService;

    @Autowired
    public ApplicationEndpoint(ApplicationStatusMapper applicationStatusMapper, EventService eventService,
                               Employee_TasksService employee_tasksService, TaskService taskService,
                               NotificationService notificationService, EmployerService employerService,
                               EmployeeService employeeService, TokenService tokenService,
                               NotificationMapper notificationMapper, EventMapper eventMapper) {
        this.applicationStatusMapper = applicationStatusMapper;
        this.eventService = eventService;
        this.employee_tasksService = employee_tasksService;
        this.taskService = taskService;
        this.notificationService = notificationService;
        this.employerService = employerService;
        this.employeeService = employeeService;
        this.tokenService = tokenService;
        this.notificationMapper = notificationMapper;
        this.eventMapper = eventMapper;
    }


    @PutMapping(value = "/apply")
    @ApiOperation(value = "Handle application from employee and send Notification", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "http://localhost:4200")
    public void apply(@Valid @RequestBody ApplicationDto applicationDto, @RequestHeader String authorization){
        LOGGER.info("PUT /api/v1/applications/apply message: {}", applicationDto);
        String mail = tokenService.getEmailFromHeader(authorization);
        Employee employee = employeeService.findOneByEmail(mail);
        Task task = taskService.findOneById(applicationDto.getTask());
        Event event = eventService.findByTask(task);
        Employer employer = employerService.findByEvent(event);

        Notification existingApplication = notificationService.findFirstByEvent_IdAndSender_Id(event.getId(), employee.getId());
        if(!(existingApplication == null || existingApplication.getType().equalsIgnoreCase(NotificationType.NOTIFICATION.name()))){
            throw new AlreadyHandledException("Sie haben sich bereits für dieses Event beworben.");
        }

        employee_tasksService.applyForTask(employee, task);
        Notification application = new Notification();
        application.setEvent(event);
        application.setMessage(applicationDto.getMessage());
        application.setRecipient(employer.getProfile());
        application.setSender(employee.getProfile());
        application.setSeen(false);
        application.setTask(task);
        application.setType(NotificationType.APPLICATION.name());

        Notification notification = new Notification();
        notification.setEvent(event);
        notification.setMessage("Es gibt eine neue Bewerbung für das Event: " + event.getTitle());
        notification.setRecipient(employer.getProfile());
        notification.setSender(null);
        notification.setSeen(false);
        notification.setTask(task);
        notification.setType(NotificationType.NOTIFICATION.name());

        notificationService.createNotification(application);
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
            notification.setMessage(String.format("Deine Bewerbung für das Event \"%s\" wurde akzeptiert", event.getTitle()));
            notification.setType(NotificationType.EVENT_ACCEPTED.name());
            employeeService.deleteTime(applicationStatusDto.getEmployee(), applicationStatusDto.getTask());  // delete time
        }else{
            notification.setMessage(String.format("Deine Bewerbung für das Event \"%s\" wurde abgelehnt", event.getTitle()));
            notification.setType(NotificationType.EVENT_DECLINED.name());
        }
        employee_tasksService.updateStatus(employee_tasks);
        notificationService.deleteNotification(applicationStatusDto.getNotification(), authorization);
        notificationService.createNotification(notification);
    }

    @GetMapping(value = "/events/{id}")
    @ApiOperation(value = "Get all applications for an Event", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public Set<SimpleNotificationDto> getAllApplicationsForEvent(@PathVariable Long id) {
        LOGGER.info("Get /api/v1/applications/events/{}", id);
        return notificationMapper.notificationsToSimpleNotificationsDtos(notificationService.findAllApplicationsByEvent_Id(id));
    }

    @GetMapping(value = "/applied")
    @ApiOperation(value = "Get all applications for an Event", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    @Transactional
    public List<DetailedEventDto> getAppliedEvents(@RequestHeader String authorization){
        LOGGER.info("Get /api/v1/applications/applied");
        Employee emp = tokenService.getEmployeeFromHeader(authorization);
        return eventMapper.eventsToDetailedEventDtos(eventService.findAllAppliedEvents(emp.getId()));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete application with id", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public void deleteApplication(@PathVariable Long id, @RequestHeader String authorization) {
        LOGGER.info("DELETE /api/v1/applications/{}", id);
        notificationService.deleteApplication(id, authorization);
    }
    @DeleteMapping(value = "/task/{id}")
    @ApiOperation(value = "Delete job with task id", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public void deleteJob(@PathVariable Long id, @RequestHeader String authorization) {
        notificationService.deleteEmployeeFromTask(id, authorization);
    }
}
