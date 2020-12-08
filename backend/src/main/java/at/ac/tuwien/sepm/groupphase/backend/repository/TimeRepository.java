package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TimeRepository extends JpaRepository<Time, Long> {

    Set<Time> findByEmployee_Profile_Id(Long id);

}