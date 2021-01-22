package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;

import java.util.List;
import java.util.Set;

public interface EmployeeService {

    /**
     * Creates an employee with the given details
     * @param employee to create
     * @return the ID of the created employee
     */
    Long createEmployee(Employee employee);

    /**
     * Find an employee by email
     * @param email to look for
     * @return the employee
     */
    Employee findOneByEmail(String email);

    /**
     * Find an employee by id
     * @param id to look for
     * @return the employee
     */
    Employee findOneById(Long id);

    /**
     * Update an employee
     * @param employee to update
     * @return the ID of the updated employee
     */
    Long updateEmployee(Employee employee);

    /**
     * Find all employees
     *
     * @return list of all employees
     */
    List<Employee> findAll();

    /**
     * Delete available time when employee gets accepted for an event
     * @param employee_id look for times of this employee
     * @param task_id look for start and end time of event where this task belongs to
     */
    void deleteTime(Long employee_id, Long task_id);

    /**
     * Checks if the employee still has upcoming or current tasks which they have been accepted for
     *
     * @param email of the employee
     * @return true if there are still upcoming tasks
     */
    boolean hasUpcomingTasks(String email);

    /**
     * Delete given employee (including their employee_tasks, interests, notifications)
     *
     * @param email of the employer
     */
    void deleteByEmail(String email);

    List<Employee> findEmployeeByInterestArea(Set<String> interestAreas);
}