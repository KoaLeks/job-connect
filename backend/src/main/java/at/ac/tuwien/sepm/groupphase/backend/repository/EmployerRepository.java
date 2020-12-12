package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    /**
     * Find an employer with a certain email address
     *
     * @param email to look for
     * @return the employer
     */
    Employer findByProfile_Email(String email);
}
