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


    @Query("SELECT distinct event from Event event " +
        "inner join Task task on event.id=task.event.id " +
        "inner join InterestArea area on task.interestArea.id=area.id " +
        "where (?1 is null or lower(event.title) like lower(?1)) and " +
        "(?2 is null or event.employer.id=?2) and " +
        "(?3 is null or event.start <= ?3) and " +
        "(?4 is null or area.id=?4) and " +
        "(?5 is null or task.paymentHourly >= ?5)"
    )
    List<Event> searchEventsBySearchEventDto(String title, Long employerId, LocalDateTime start, Long interestAreaId
    ,Long payment);

}
