package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {

    /**
     * Finds all the interests of the employee with the given id
     *
     * @param id of the employee
     * @return Set of interests of the employee with the given id
     */
    Set<Interest> findByEmployee_Id(Long id);

    /**
     * Deletes all the interests of the employee with the given profile email
     *
     * @param email of the employee profile
     */
    void deleteInterestsByEmployee_Profile_Email(String email);

    List<Interest> findAllByEmployeeId(Long id);
}