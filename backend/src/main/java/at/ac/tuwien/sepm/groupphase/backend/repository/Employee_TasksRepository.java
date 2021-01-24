package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee_Tasks;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

import java.time.LocalDateTime;

@Repository
public interface Employee_TasksRepository extends JpaRepository<Employee_Tasks, Long> {

    /**
     * Finds the Employee_Tasks including the task the employee has applied to
     *
     * @param employee the employee who applied
     * @param task the task the employee applied to
     * @return Employee_Tasks object including information about the application of the employee to the task
     */
    Employee_Tasks findFirstByEmployeeAndTask(Employee employee, Task task);

    /**
     * Finds the Employee_Tasks including the task (with the given id) the employee (with given profile id) has applied to
     *
     * @param employee the id of the employee profile who applied
     * @param task the id of the task the employee applied to
     * @return Employee_Tasks object including information about the application of the employee
     *         with the given employee profile id to the task with the given task id
     */
    Employee_Tasks findFirstByEmployee_Profile_IdAndTask_Id(Long employee, Long task);

    Set<Employee_Tasks> findAllByTask_IdAndAcceptedIsTrue(Long task);

    /**
     * Count how many accepted tasks a certain employee has after a point in time
     *
     * @param employeeId to count the employees tasks
     * @param time after which there are still active tasks
     * @return number of entries
     */
    @Query("SELECT COUNT (et.id) FROM Employee_Tasks et LEFT JOIN Task t on et.task.id = t.id LEFT JOIN Event e on " +
        "t.event.id = e.id WHERE et.employee.id = ?1 AND et.accepted = TRUE AND e.end > ?2")
    long countAllByEmployeeAndAcceptedAndEndAfter(Long employeeId, LocalDateTime time);

    /**
     * Deletes all Employee_Tasks where the employee profile has the given email
     *
     * @param email of the employee profile
     */
    void deleteEmployee_TasksByEmployee_Profile_Email(String email);

    /**
     * Delete the one Employee_Tasks where the employee profile has the given email and the task has the given id
     *
     * @param email of the employee profile
     * @param id of the task
     */
    void deleteEmployee_TaskByEmployee_Profile_Email_AndTask_Id(String email, Long id);

    /**
     * Counts all accepted or declined employees of the given task
     *
     * @param taskId id of the task
     * @param accepted either true or false
     * @return the number of accepted or declined employees of the given task
     */
    long countAllByTask_IdAndAccepted(Long taskId, Boolean accepted);
}
