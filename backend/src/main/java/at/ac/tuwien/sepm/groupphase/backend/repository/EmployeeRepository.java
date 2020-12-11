package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find an employee with a certain email address
     * @param email to look for
     * @return the employee
     */
    Employee findByProfile_Email(String email);

    /**
     * Find all employees with their interests
     *
     * @return List of employees
     */
    @Query(value = "FROM Employee e ORDER BY e.profile.firstName")
    List<Employee> getAllEmployeesAndFetchInterests();
}
