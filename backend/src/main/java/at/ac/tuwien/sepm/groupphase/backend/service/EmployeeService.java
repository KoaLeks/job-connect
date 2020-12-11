package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.exception.UniqueConstraintException;

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
     * Update an employee
     * @param employee to update
     * @return the ID of the updated employee
     */
    Long updateEmployee(Employee employee);
}