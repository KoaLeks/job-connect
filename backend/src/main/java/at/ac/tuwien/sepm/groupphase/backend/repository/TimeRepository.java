package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

public interface TimeRepository extends JpaRepository<Time, Long> {

    Set<Time> findByEmployee_Profile_Id(Long id);

    /**
     * This methods deletes all the records whose 'finalEndDate' date is less than 'expiryDate'
     */
    @Modifying
    @Transactional
    void deleteByFinalEndDateBefore(LocalDateTime expiryDate);

}