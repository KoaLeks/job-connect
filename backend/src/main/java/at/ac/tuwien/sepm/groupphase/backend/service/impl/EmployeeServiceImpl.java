package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployeeRepository;
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
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, ProfileService profileService, ProfileRepository profileRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
    }

    @Override
    public Long createEmployee(Employee employee) throws UniqueConstraintException {
        employee.getProfile().setPassword(passwordEncoder.encode(employee.getProfile().getPassword()));
        employee.setId(profileService.createProfile(employee.getProfile()));
        return employeeRepository.save(employee).getId();
    }

    @Override
    public Employee findOneByEmail(String email) {
        LOGGER.info("Find employee with email {}", email);
        Employee employee = employeeRepository.findByProfile_Email(email);
        if (employee != null) return employee;
        else throw new NotFoundException(String.format("Could not find employee with email %s", email));
    }

    @Override
    public Long updateEmployee(Employee employee) {
        LOGGER.info("Update employee: {}", employee);

        Profile profile = profileService.findProfileByEmail(employee.getProfile().getEmail());
        if(!employee.getProfile().getPassword().isBlank())
            employee.getProfile().setPassword(passwordEncoder.encode(employee.getProfile().getPassword()));
        else
            employee.getProfile().setPassword(profile.getPassword());

        employee.setId(profile.getId());
        employee.getProfile().setId(profile.getId());
        employeeRepository.save(employee);
        return profileRepository.save(employee.getProfile()).getId();
    }
}
