package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.hibernate.WrongClassException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

    /**
     * Finds the event with the given task
     *
     * @param task belonging to the event
     * @return the event the task belongs to
     */
    Event findFirstByTasks(Task task);

    /**
     * Finds all events stored in the database
     *
     * @return a List of all the stored Events
     */
    List<Event> findAll();

    /**
     * Count events of a certain employer and which end after a certain point in time
     *
     * @param id of the employer
     * @param localDateTime end datetime
     * @return number of events
     */
    long countEventsByEmployer_IdAndEndAfter(Long id, LocalDateTime localDateTime);

    /**
     * Deletes all events of a certain employer
     *
     * @param email of the employer
     */
    void deleteEventsByEmployer_Profile_Email(String email);

    /**
     * Finds all Events where the employee profile with the given id is applied to
     *
     * @param id of the employee profile
     * @return List of Events the employee profile with given id is applied to
     */
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
     *
     * @return list of events with free job slots
     */
    @Query("select event from Event event " +
        "inner join Task task on task.event.id=event.id " +
        "group by event,task.id " +
        "having sum(task.employeeCount) > (select count(et.accepted) from Employee_Tasks et where et.accepted=true and et.task.id=task.id)")
    List<Event> findEventsWithFreeAvailableSlots();


    /**
     * Finds events that start between available times of given user and has similar interest areas as user
     * @param userId Id of user to match times and interests with
     * @return list of events in which user has time and interest
     */
    @Query("select event from Event event inner join Time time on time.employee.id=?1 inner join Interest interest on " +
        "interest.employee.id=?1 inner join InterestArea intArea on intArea.id=interest.interestArea.id inner join Task task " +
        "on task.interestArea.id=intArea.id where time.start < event.start and event.start < time.end")
    List<Event> findEventsByUsersTime(Long userId);

    /**
     * Finds events after given criteria
     *
     * @param title event title to search for
     * @param employerId employerId to find events from that employer
     * @param start period start to search events
     * @param end period end to search events that start between start and end
     * @param interestAreaId id of interestArea to find events of that area
     * @param payment minimum payment per hour
     * @param state state to filter events with
     * @return list of events with given criteria
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
            "(?7 IS NULL OR ad.state=?7) AND e.end > CURRENT_TIMESTAMP()",
        nativeQuery = true)
    List<Event> searchEventsBySearchEventDto(String title, Long employerId, String start, String end,
                                             Long interestAreaId, Long payment, String state);

    /**
     * Finds all events of the employer with his profile id
     *
     * @param employerId the profile id of the employer
     * @return List of Events of the employer with the given id
     */
    @Query(value = "SELECT * FROM event e " +
        "WHERE e.employer_profile_id = ?1 " +
        "AND e.start > CURRENT_TIMESTAMP()", nativeQuery = true)
    List<Event> findALlByEmployerId(Long employerId);

}
