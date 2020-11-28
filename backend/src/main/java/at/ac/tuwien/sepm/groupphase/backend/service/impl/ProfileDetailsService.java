package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ProfileRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class ProfileDetailsService implements ProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileDetailsService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.debug("Load all profiles by email");
        try {
            Profile profile = findProfileByEmail(email);

            List<GrantedAuthority> grantedAuthorities;
            if (profile.isEmployer())
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYER", "ROLE_EMPLOYEE");
            else
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");

            return new User(profile.getEmail(), profile.getPassword(), grantedAuthorities);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public Profile findProfileByEmail(String email) {
        LOGGER.debug("Find profile by email");
        Profile profile = profileRepository.findProfileByEmail(email);
        if (profile != null) return profile;
        throw new NotFoundException(String.format("Could not find the user with the email address %s", email));
    }
}
