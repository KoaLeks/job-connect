package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SearchEventDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

import java.util.List;

public interface EventService {

    /**
     * Saves new event in database.
     *
     * @param event that is being saved to database.
     * @return the newly created event.
     */
    Event saveEvent(Event event);


    /**
     * Finds events with given search criteria.
     *
     * @param searchEventDto given search criteria dto
     * @return list of found events or if given criteria is empty all events
     */
    List<Event> findAll(SearchEventDto searchEventDto);


    /**
     * Finds event by id
     *
     * @param id of event to find
     * @return event with given id
     */
    Event findById(Long id);

    /**
     * Finds event by task
     *
     * @param task specific to event
     * @return event with given task
     */
    Event findByTask(Task task);

    /**
     * Delete event by id
     *
     * @param id of event to delete
     */
    void deleteEventById(Long id);

    /**
     * Finds all events the employee with given id has applied to
     *
     * @param id of the employee profile
     * @return List of all events the employee applied to
     */
    List<Event> findAllAppliedEvents(Long id);

    /**
     * Finds all events of the employer with his profile id
     *
     * @param employerId the profile id of the employer
     * @return List of Events of the employer with the given id
     */
    List<Event> findByEmployerId(Long employerId);
}
