package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployerRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployerService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class EmployerServiceImpl implements EmployerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EmployerRepository employerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @Autowired
    public EmployerServiceImpl(EmployerRepository employerRepository, PasswordEncoder passwordEncoder, ProfileService profileService) {
        this.employerRepository = employerRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
    }

    @Override
    public Long createEmployer(Employer employer) throws UniqueConstraintException {
        employer.getProfile().setPassword(passwordEncoder.encode(employer.getProfile().getPassword()));
        employer.setId(profileService.createProfile(employer.getProfile()));
        return employerRepository.save(employer).getId();
    }

    @Override
    public Employer findOneByEmail(String email) {
        LOGGER.info("Find employer with email {}", email);
        Employer employer = employerRepository.findByProfile_Email(email);
        if (employer != null) return employer;
        else throw new NotFoundException(String.format("Could not find employer with email %s", email));
    }
}
