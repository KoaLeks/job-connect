package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployerRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProfileRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployerService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployerServiceImpl implements EmployerService {

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
}
