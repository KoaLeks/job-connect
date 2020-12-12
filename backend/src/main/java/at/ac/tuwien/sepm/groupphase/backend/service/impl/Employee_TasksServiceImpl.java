package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee_Tasks;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import at.ac.tuwien.sepm.groupphase.backend.exception.AlreadyHandledException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.Employee_TasksRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.Employee_TasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class Employee_TasksServiceImpl implements Employee_TasksService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Employee_TasksRepository employee_tasksRepository;

    public Employee_TasksServiceImpl(Employee_TasksRepository employee_tasksRepository) {
        this.employee_tasksRepository = employee_tasksRepository;
    }

    @Override
    public Long applyForTask(Employee employee, Task task) {
        LOGGER.debug("Create application from employee {} for task {}", employee, task);
        Employee_Tasks employee_tasks = new Employee_Tasks();
        employee_tasks.setEmployee(employee);
        employee_tasks.setTask(task);
        if(employee_tasksRepository.findFirstByEmployeeAndTask(employee, task) == null){
            return employee_tasksRepository.save(employee_tasks).getId();
        }else{
            throw new AlreadyHandledException(String.format("Already applied for Task: %s", task));
        }

    }

    @Override
    public void updateStatus(Employee_Tasks employee_tasks) {
        LOGGER.debug("Update status of Application: {}", employee_tasks);
        Employee_Tasks toUpdate = employee_tasksRepository.findFirstByEmployeeAndTask(employee_tasks.getEmployee(), employee_tasks.getTask());
        if(toUpdate == null) throw new NotFoundException(String.format("Employee_Tasks application not found: %s", employee_tasks));
        if(toUpdate.getAccepted() != null){
            String status = toUpdate.getAccepted() ? "accepted" : "declined";
            throw new AlreadyHandledException(String.format("The Application from %s for task %s already was %s", toUpdate.getEmployee().getProfile().getEmail(), toUpdate.getTask().getId(), status));
        }
        toUpdate.setAccepted(employee_tasks.getAccepted());
        employee_tasksRepository.save(toUpdate);
    }
}
