package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Event findFirstByTasks(Task task);


    @Query(value =
        "SELECT DISTINCT e.id,e.description, e.end, e.start, e.title, e.address_id, e.employer_profile_id FROM event e " +
        "INNER JOIN task t ON e.id=t.event_id " +
        "INNER JOIN interest_area a ON t.interest_area_id=a.id " +
        "WHERE (?1 IS NULL OR e.title LIKE ?1) AND " +
        "(?2 IS NULL OR e.employer_profile_id=?2) AND " +
            "(FORMATDATETIME(e.start, 'yyyy-MM-dd') between ?3 and ?4) AND " +
            "(?5 IS NULL OR a.id=?5) AND " +
            "(?6 IS NULL OR t.payment_hourly>=?6)",
        nativeQuery = true)
    List<Event> searchEventsBySearchEventDto(String title, Long employerId, String start, String end,
                                             Long interestAreaId, Long payment);

}
