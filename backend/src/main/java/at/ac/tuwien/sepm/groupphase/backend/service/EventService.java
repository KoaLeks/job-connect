package at.ac.tuwien.sepm.groupphase.backend.service;

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
     * Find all event entries.
     *
     * @return  list of all event entries
     */
    List<Event> findAll();

    /**
     * Find event by ID
     * @return event with given id
     */
    Event findById(Long id);

    /**
     * Find event by task
     * @return event with given task
     */
    Event findByTask(Task task);

    /**
     * Delete event by id
     */
    void deleteEventById(Long id);
}
