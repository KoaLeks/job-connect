package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;
import at.ac.tuwien.sepm.groupphase.backend.repository.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final InterestRepository interestRepository;
    private final TimeRepository timeRepository;
    private final Employee_TasksRepository employee_tasksRepository;
    private final EventRepository eventRepository;
    private final TaskRepository taskRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
                               ProfileService profileService, ProfileRepository profileRepository, InterestRepository interestRepository,
                               TimeRepository timeRepository, Employee_TasksRepository employee_tasksRepository, EventRepository eventRepository, TaskRepository taskRepository, NotificationRepository notificationRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        this.interestRepository = interestRepository;
        this.timeRepository = timeRepository;
        this.employee_tasksRepository = employee_tasksRepository;
        this.eventRepository = eventRepository;
        this.taskRepository = taskRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Long createEmployee(Employee employee) throws UniqueConstraintException {
        employee.getProfile().setPassword(passwordEncoder.encode(employee.getProfile().getPassword()));
        employee.setId(profileService.createProfile(employee.getProfile()));
        LOGGER.info("Service employee" + employee);
        return employeeRepository.save(employee).getId();
    }

    @Override
    public Employee findOneByEmail(String email) {
        LOGGER.info("Find employee with email {}", email);
        Employee employee = employeeRepository.findByProfile_Email(email);
        if (employee == null)
            throw new NotFoundException(String.format("Could not find employee with email %s", email));

        Set<Interest> interests = interestRepository.findByEmployee_Id(employee.getProfile().getId());
        employee.setInterests(interests);

        Set<Time> times = timeRepository.findByEmployee_Profile_Id(employee.getProfile().getId());
        employee.setTimes(times);

        timeRepository.deleteByFinalEndDateBefore(LocalDateTime.now());

        return employee;
    }

    @Override
    @Transactional
    public Employee findOneById(Long id) {
        LOGGER.info("Find employee with id {}", id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty())
            throw new NotFoundException(String.format("ArbeitnehmerIn(%s) konnte nicht gefunden werden", id));

        Set<Interest> interests = interestRepository.findByEmployee_Id(employee.get().getProfile().getId());
        employee.get().setInterests(interests);

        Set<Time> times = timeRepository.findByEmployee_Profile_Id(employee.get().getProfile().getId());
        employee.get().setTimes(times);

        Set<Employee_Tasks> tasks = Set.copyOf(employee_tasksRepository.findAll());
        employee.get().setTasks(tasks);

        timeRepository.deleteByFinalEndDateBefore(LocalDateTime.now());

        return employee.get();
    }

    @Override
    @Transactional
    public Long updateEmployee(Employee employee) {
        LOGGER.info("Update employee: {}", employee);

        Profile profile = profileService.findProfileByEmail(employee.getProfile().getEmail());

        employee.getProfile().setPassword(profile.getPassword());
        employee.setId(profile.getId());
        employee.getProfile().setId(profile.getId());

        if (employee.getTimes() != null && employee.getTimes().size() != 0) {
            Set<Time> timesFromFrontend = employee.getTimes(); //times that are sent from Frontend -> does not include deleted times by user
            Set<Time> existingTimes = timeRepository.findByEmployee_Profile_Id(employee.getProfile().getId()); // all currently saved times from database for this user
            HashSet<Long> keptIds = new HashSet<>(timesFromFrontend.size()); // these times should be saved again
            ArrayList<Long> IdsToDelete = new ArrayList<>();

            for (Time time : timesFromFrontend) {
                if (time.getId() == null) {
                    time.setEmployee(employee);
                    timeRepository.save(time);
                } else {
                    keptIds.add(time.getId());
                }
            }

            for (Time time : existingTimes) {
                if (!keptIds.contains(time.getId())) {
                    IdsToDelete.add(time.getRef_id());
                    timeRepository.deleteById(time.getId());
                }
            }

            Set<Time> newExistingTimes = timeRepository.findByEmployee_Profile_Id(employee.getProfile().getId());
            for (Time time : newExistingTimes) {
                for (Long id : IdsToDelete) {
                    if (id != -1 && time.getRef_id().equals(id)) {
                        timeRepository.deleteById(time.getId());
                    }
                }
            }

        } else {
            Set<Time> deletableTimes = timeRepository.findByEmployee_Profile_Id(employee.getProfile().getId());
            if (deletableTimes != null && deletableTimes.size() != 0) {
                for (Time time : deletableTimes) {
                    timeRepository.deleteById(time.getId());
                }
            }
        }

        employeeRepository.save(employee);

        if (employee.getInterests() != null && employee.getInterests().size() != 0) {
            Set<Interest> interests = employee.getInterests();
            Set<Interest> savedInterests = interestRepository.findByEmployee_Id(employee.getProfile().getId());
            HashSet<Long> keptIds = new HashSet<>(interests.size());
            for (Interest i : interests) {
                if (i.getId() == null) {
                    i.setEmployee(employee);
                    interestRepository.save(i);
                } else {
                    keptIds.add(i.getId());
                }
            }
            for (Interest i : savedInterests) {
                if (!keptIds.contains(i.getId())) {
                    interestRepository.deleteById(i.getId());
                }
            }
        } else {
            Set<Interest> deletableInterests = interestRepository.findByEmployee_Id(employee.getProfile().getId());
            if (deletableInterests != null && deletableInterests.size() != 0) {
                for (Interest i : deletableInterests) {
                    interestRepository.deleteById(i.getId());
                }
            }
        }

        return profileRepository.save(employee.getProfile()).getId();
    }

    @Override
    @Transactional
    public List<Employee> findAll() {
        LOGGER.info("Find all employees");
        return employeeRepository.findAllByOrderByProfile_FirstName();
    }

    @Override
    public void deleteTime(Long employee_id, Long task_id) {
        // look for event with task.id and its start and end times
        Task task = taskRepository.getOne(task_id);
        Event event = eventRepository.findFirstByTasks(task);
        LocalDateTime event_start = event.getStart();
        LocalDateTime event_end = event.getEnd();
        // look for all times for this employee
        Set<Time> times = timeRepository.findByEmployee_Profile_Id(employee_id);
        // delete time and possibly create one or two new ones
        for (Time time : times) {
            if ((time.getStart().isEqual(event_start) || time.getStart().isBefore(event_start)) &&
                (time.getEnd().isEqual(event_end) || time.getEnd().isAfter(event_end))) {

                if (time.getStart().isBefore(event_start)) {
                    Time newTime = new Time();
                    newTime.setId(null);
                    newTime.setStart(time.getStart());
                    newTime.setEnd(event_start);
                    newTime.setFinalEndDate(event_start);
                    newTime.setRef_id(-1L);
                    newTime.setEmployee(employeeRepository.getOne(employee_id));
                    newTime.setVisible(true);
                    timeRepository.save(newTime);
                }

                if (time.getEnd().isAfter(event_end)) {
                    Time newTime = new Time();
                    newTime.setId(null);
                    newTime.setStart(event_end);
                    newTime.setEnd(time.getEnd());
                    newTime.setFinalEndDate(time.getEnd());
                    newTime.setRef_id(-1L);
                    newTime.setEmployee(employeeRepository.getOne(employee_id));
                    newTime.setVisible(true);
                    timeRepository.save(newTime);
                }

                if (time.getRef_id() != -1L && time.getVisible()) {
                    // delete this time and update new one to show in frontend
                    Time updateTime = timeRepository.findByStart(time.getStart().plusDays(7));
                    updateTime.setFinalEndDate(time.getFinalEndDate());
                    updateTime.setVisible(true);
                    timeRepository.save(updateTime);
                }
                timeRepository.delete(time);
            }
        }
    }

    @Override
    public boolean hasUpcomingTasks(String email) {
        LOGGER.info("Check if employee has upcoming tasks: {}", email);
        Employee employee = employeeRepository.findByProfile_Email(email);
        return employee_tasksRepository.countAllByEmployeeAndAcceptedAndEndAfter(employee.getId(), LocalDateTime.now()) > 0;
    }

    @Override
    public void deleteByEmail(String email) {
        LOGGER.info("Delete employee(+their employee_tasks, interests, notifications): {}", email);
        employee_tasksRepository.deleteEmployee_TasksByEmployee_Profile_Email(email);
        notificationRepository.deleteNotificationsByRecipient_EmailEqualsOrSender_EmailEquals(email, email);
        interestRepository.deleteInterestsByEmployee_Profile_Email(email);
        employeeRepository.deleteEmployeeByProfile_Email(email);
        profileRepository.deleteByEmail(email);
    }

    @Override
    public List<Employee> findEmployeeByInterestArea(Set<String> interestAreas) {
        return employeeRepository.findEmployeesByInterestArea(interestAreas);
    }
}