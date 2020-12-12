package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employer;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;


public interface EmployerService {

    /**
     * Creates an employer with the given details
     *
     * @param employer to create
     * @return the ID of the created employer
     */
    Long createEmployer(Employer employer);

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
     * @return the ID of the updated employer
     */
    Long updateEmployer(Employer employer);

    Employer findByEvent(Event event);
}