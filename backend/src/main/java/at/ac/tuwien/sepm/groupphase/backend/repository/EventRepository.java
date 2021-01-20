package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "SELECT * " +
        "FROM event e " +
        "INNER JOIN task t ON t.event_id=e.id " +
        "INNER JOIN employee_tasks et ON et.task_id=t.id " +
        "WHERE et.employee_profile_id=?1 "+
     //   "AND e.start > CURRENT_TIMESTAMP() " +
        "ORDER BY e.start", nativeQuery = true)
    List<Event> findAllAppliedEvents(Long id);



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
        "LEFT JOIN task t ON e.id=t.event_id " +
        "LEFT JOIN interest_area a ON t.interest_area_id=a.id " +
            "LEFT JOIN address ad ON e.address_id=ad.id " +
        "WHERE (?1 IS NULL OR lower(e.title) LIKE lower(?1)) AND " +
        "(?2 IS NULL OR e.employer_profile_id=?2) AND " +
            "(FORMATDATETIME(e.start, 'yyyy-MM-dd') between ?3 and ?4) AND " +
            "(?5 IS NULL OR a.id=?5) AND " +
            "(?6 IS NULL OR t.payment_hourly>=?6) AND " +
            "(?7 IS NULL OR ad.state=?7)",
        nativeQuery = true)
    List<Event> searchEventsBySearchEventDto(String title, Long employerId, String start, String end,
                                             Long interestAreaId, Long payment, String state);

}
