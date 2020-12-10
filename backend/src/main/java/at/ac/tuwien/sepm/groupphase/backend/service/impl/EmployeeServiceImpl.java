package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployeeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProfileRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final InterestRepository interestRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
                               ProfileService profileService, ProfileRepository profileRepository, InterestRepository interestRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        this.interestRepository = interestRepository;
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
        if (employee == null) throw new NotFoundException(String.format("Could not find employee with email %s", email));

        Set<Interest> interests = interestRepository.findByEmployee_Id(employee.getProfile().getId());
        employee.setInterests(interests);

        return employee;
    }

    @Override
    public Long updateEmployee(Employee employee) {
        LOGGER.info("Update employee: {}", employee);

        Profile profile = profileService.findProfileByEmail(employee.getProfile().getEmail());

        employee.getProfile().setPassword(profile.getPassword());
        employee.setId(profile.getId());
        employee.getProfile().setId(profile.getId());
        employeeRepository.save(employee);

        if(employee.getInterests() != null && employee.getInterests().size() != 0) {
            Set<Interest> interests = employee.getInterests();
            Set<Interest> savedInterests = interestRepository.findByEmployee_Id(employee.getProfile().getId());
            HashSet<Long> keptIds = new HashSet<>(interests.size());
            for (Interest i : interests) {
                if(i.getId() == null) {
                    i.setEmployee(employee);
                    interestRepository.save(i);
                } else {
                 keptIds.add(i.getId());
                }
            }
            for (Interest i : savedInterests) {
                if(!keptIds.contains(i.getId())) {
                    interestRepository.deleteById(i.getId());
                }
            }
        } else {
            Set<Interest> deletableInterests = interestRepository.findByEmployee_Id(employee.getProfile().getId());
            if(deletableInterests != null && deletableInterests.size() != 0) {
                for (Interest i : deletableInterests) {
                    interestRepository.deleteById(i.getId());
                }
            }
        }

        return profileRepository.save(employee.getProfile()).getId();
    }
}
