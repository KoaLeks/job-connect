package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    Event findFirstByTasks(Task task);



    /**
     * Finds events that have at least one free job
     * @return list of events with free job slots
     */
    @Query("select event from Event event " +
        "inner join Task task on task.event.id=event.id " +
        "group by event,task.id " +
        "having sum(task.employeeCount) > (select count(et.accepted) from Employee_Tasks et where et.accepted=true and et.task.id=task.id)")
    List<Event> findEventsWithFreeAvailableSlots();


    /**
     * Finds events that start between available times of given user
     * @param userId Id of user to match times with
     * @return list of events in which user has time
     */
    @Query(value =
        "SELECT e.id,e.description, e.end, e.start, e.title, e.address_id, e.employer_profile_id FROM event e, " +
            "time t where t.employee_profile_id=?1 and (FORMATDATETIME(e.start, 'yyyy-MM-dd') between t.start and t.end)",
        nativeQuery = true)
    List<Event> findEventsByUsersTime(Long userId);

    /**
     * Finds events after given criterias
     * @param title event title to search for
     * @param employerId employerId to find events from that employer
     * @param start period start to search events
     * @param end period end to search events that start between start and end
     * @param interestAreaId id of interestArea to find events of that area
     * @param payment minimum payment per hour
     * @param state state to filter events with
     * @return list of events with given criterias
     */
    @Query(value =
        "SELECT DISTINCT e.id,e.description, e.end, e.start, e.title, e.address_id, e.employer_profile_id FROM event e " +
        "INNER JOIN task t ON e.id=t.event_id " +
        "INNER JOIN interest_area a ON t.interest_area_id=a.id " +
            "INNER JOIN address ad on e.address_id=ad.id " +
        "WHERE (?1 IS NULL OR e.title LIKE ?1) AND " +
        "(?2 IS NULL OR e.employer_profile_id=?2) AND " +
            "(FORMATDATETIME(e.start, 'yyyy-MM-dd') between ?3 and ?4) AND " +
            "(?5 IS NULL OR a.id=?5) AND " +
            "(?6 IS NULL OR t.payment_hourly>=?6) AND " +
            "(?7 IS NULL OR ad.state=?7)",
        nativeQuery = true)
    List<Event> searchEventsBySearchEventDto(String title, Long employerId, String start, String end,
                                             Long interestAreaId, Long payment, String state);

}
