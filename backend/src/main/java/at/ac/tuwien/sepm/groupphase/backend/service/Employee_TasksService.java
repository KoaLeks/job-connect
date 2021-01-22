package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee_Tasks;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

public interface Employee_TasksService {

    /**
     * Creates Employee_Tasks object and therefore applies employee to a task
     *
     * @param employee applying to the task
     * @param task task the employee applies to
     * @return the id of the created Employee_Tasks object
     */
    Long applyForTask(Employee employee, Task task);

    /**
     * Updates the status of the application to accepted or declined
     * @param employee_tasks to update to status of
     */
    void updateStatus(Employee_Tasks employee_tasks);
}
