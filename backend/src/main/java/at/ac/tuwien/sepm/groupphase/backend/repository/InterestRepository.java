package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {

    Set<Interest> findByEmployee_Id(Long id);

    void deleteInterestsByEmployee_Profile_Email(String email);
}