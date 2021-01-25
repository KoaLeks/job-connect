package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Time;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {

    /**
     * Finds all time records for this employee
     *
     * @param id of the employee profile
     * @return Set of time of this employee
     */
    Set<Time> findByEmployee_Profile_Id(Long id);

    /**
     * Finds a specific time record with given start value
     *
     * @param start of the record to find
     * @return the time entry with the start value
     */
    Time findByStart(LocalDateTime start);

    /**
     * This methods deletes all the records whose 'finalEndDate' date is less than 'expiryDate' -> delete times from past
     *
     * @param expiryDate by which a record gets deleted if its 'finalEndDate' is smaller
     */
    @Modifying
    @Transactional
    void deleteByFinalEndDateBefore(LocalDateTime expiryDate);

    List<Time> findAllByEmployeeId(Long id);

}