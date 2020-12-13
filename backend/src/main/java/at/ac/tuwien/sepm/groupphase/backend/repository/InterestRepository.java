package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    Set<Interest> findByEmployee_Id(Long id);

}