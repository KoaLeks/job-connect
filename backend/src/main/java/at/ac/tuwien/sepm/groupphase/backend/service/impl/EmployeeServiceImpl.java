package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployeeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProfileRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TimeRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
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

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
                               ProfileService profileService, ProfileRepository profileRepository, InterestRepository interestRepository,
                               TimeRepository timeRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        this.interestRepository = interestRepository;
        this.timeRepository = timeRepository;
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

        Set<Interest> interests = interestRepository.findByEmployees_Id(employee.getProfile().getId());
        employee.setInterests(interests);

        Set<Time> times = timeRepository.findByEmployee_Profile_Id(employee.getProfile().getId());
        employee.setTimes(times);

        timeRepository.deleteByFinalEndDateBefore(LocalDateTime.now());

        return employee;
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
        return profileRepository.save(employee.getProfile()).getId();
    }


}