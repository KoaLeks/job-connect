package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find an employee with a certain email address
     *
     * @param email to look for
     * @return the employee
     */
    Employee findByProfile_Email(String email);

    /**
     * Find all employees ordered by their first name
     *
     * @return all employees ordered by first name
     */
    List<Employee> findAllByOrderByProfile_FirstName();
}
