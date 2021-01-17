package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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


    @Query("select employee from Employee employee " +
        "inner join Interest interest on interest.employee.id=employee.id " +
        "inner join Event event on event.id=?1 " +
        "inner join Task task on task.event.id=event.id " +
        "inner join InterestArea intArea on task.interestArea.id=intArea.id " +
        "inner join Time time on time.employee.id=employee.id " +
        "where time.start < event.start and event.start < time.end and intArea.id=interest.interestArea.id")
    List<Employee> getAvailableEmployeesByEvent(Long eventId);
}
