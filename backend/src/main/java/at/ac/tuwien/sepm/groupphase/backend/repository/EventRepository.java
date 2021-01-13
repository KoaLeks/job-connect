package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findFirstByTasks(Task task);
    List<Event> findAll();

    /**
     * Count events of a certain employer and which end after a certain point in time
     * @param id of the employer
     * @param localDateTime end datetime
     * @return number of events
     */
    long countEventsByEmployer_IdAndEndAfter(Long id, LocalDateTime localDateTime);

    /**
     * Deletes all events of a certain employer
     * @param email of the employer
     */
    void deleteEventsByEmployer_Profile_Email(String email);
}
