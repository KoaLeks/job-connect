package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Time;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

    Set<Time> findByEmployee_Profile_Id(Long id);
    Time findByStart(LocalDateTime start);

    /**
     * This methods deletes all the records whose 'finalEndDate' date is less than 'expiryDate' -> delete times from past
     */
    @Modifying
    @Transactional
    void deleteByFinalEndDateBefore(LocalDateTime expiryDate);

}