package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee_Tasks;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface Employee_TasksRepository extends JpaRepository<Employee_Tasks, Long> {
    Employee_Tasks findFirstByEmployeeAndTask(Employee employee, Task task);
    Employee_Tasks findFirstByEmployee_Profile_IdAndTask_Id(Long employee, Long task);
    Set<Employee_Tasks> findAllByTask_Id(Long task);
}
