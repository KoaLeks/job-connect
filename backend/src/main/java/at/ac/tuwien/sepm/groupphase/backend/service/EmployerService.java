package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;
import java.util.List;

public interface EmployerService {

    /**
     * Creates an employer with the given details
     *
     * @param employer to create
     * @return the id of the created employer
     */
    Long createEmployer(Employer employer);

    /**
     * Finds an employer by his id
     *
     * @param id of the employer to find
     * @return the employer
     */
    Employer findOneById(Long id);

    /**
     * Find an employer by email
     *
     * @param email to look for
     * @return the employer
     */
    Employer findOneByEmail(String email);

    /**
     * Update an employer
     *
     * @param employer to update
     * @return the id of the updated employer
     */
    Long updateEmployer(Employer employer);

    /**
     * Finds the employer/creator of a given event
     *
     * @param event used to find the employer
     * @return the employer
     */
    Employer findByEvent(Event event);

    /**
     * Find all employers
     *
     * @return list of all employers
     */
    List<Employer> findAll();

    /**
     * Checks if the employer still has events which did not start or are not over yet
     *
     * @param email of the employer
     * @return true if there are still active events
     */
    boolean hasActiveEvents(String email);

    /**
     * Delete given employer (including their events, tasks, notifications)
     *
     * @param email of the employer
     */
    void deleteByEmail(String email);
}