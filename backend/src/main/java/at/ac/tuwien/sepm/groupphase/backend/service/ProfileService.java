package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.exception.PasswordsNotMatchingException;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ProfileService extends UserDetailsService {

    /**
     * Find a user in the context of Spring Security based on the email address
     * <p>
     * For more information have a look at this tutorial:
     * https://www.baeldung.com/spring-security-authentication-with-a-database
     *
     * @param email the email address
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exists
     */
    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * Find a profile based on the email address
     *
     * @param email the email address
     * @return a profile
     */
    Profile findProfileByEmail(String email);

    /**
     * Find a profile by id
     *
     * @param id to look for
     * @return the profile
     */
    Profile findOneById(Long id);

    /**
     * Creates a profile and returns its id
     *
     * @param profile to create
     * @return the id of the created profile
     */
    Long createProfile(Profile profile);

    /**
     * Check if email password combination is correct. Used to update a users password.
     *
     * @param email of the profile
     * @param password of the profile
     * @return true if the password is correct, false otherwise
     */
    void checkIfValidCurrentPassword(String email, String password) throws PasswordsNotMatchingException;

    /**
     * Update the profile details
     *
     * @param profileToEdit values to update
     * @return the ID of the updated user profile
     */
    Long updateProfile(Profile profileToEdit);
}
