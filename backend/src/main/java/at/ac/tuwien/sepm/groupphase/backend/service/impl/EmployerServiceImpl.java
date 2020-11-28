package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.repository.EmployerRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProfileRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployerServiceImpl(EmployerRepository employerRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.employerRepository = employerRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Long createEmployer(Employer employer) {
        employer.getProfile().setPassword(passwordEncoder.encode(employer.getProfile().getPassword()));
        Profile profile = profileRepository.save(employer.getProfile());
        employer.setId(profile.getId());
        return employerRepository.save(employer).getId();
    }
}
