package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee_Tasks;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface Employee_TasksRepository extends JpaRepository<Employee_Tasks, Long> {
    Employee_Tasks findFirstByEmployeeAndTask(Employee employee, Task task);

    Employee_Tasks findFirstByEmployee_Profile_IdAndTask_Id(Long employee, Long task);

    /**
     * Count how many accepted tasks a certain employee has after a point in time
     * @param employeeId to count the employees tasks
     * @param time after which there are still active tasks
     * @return number of entries
     */
    @Query("SELECT COUNT (et.id) FROM Employee_Tasks et LEFT JOIN Task t on et.task.id = t.id LEFT JOIN Event e on " +
        "t.event.id = e.id WHERE et.employee.id = ?1 AND et.accepted = TRUE AND e.end > ?2")
    long countAllByEmployeeAndAcceptedAndEndAfter(Long employeeId, LocalDateTime time);

    void deleteEmployee_TasksByEmployee_Profile_Email(String email);

    void deleteEmployee_TaskByEmployee_Profile_Email_AndTask_Id(String email, Long id);
}
