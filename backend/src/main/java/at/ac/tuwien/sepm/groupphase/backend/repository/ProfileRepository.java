package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    /**
     * Find a user profile with a certain email address
     *
     * @param email to look for
     * @return the profile
     */
    Profile findProfileByEmail(String email);

    /**
     * Delete a user profile with a certain email address
     *
     * @param email of profile to delete
     */
    void deleteByEmail(String email);
}
