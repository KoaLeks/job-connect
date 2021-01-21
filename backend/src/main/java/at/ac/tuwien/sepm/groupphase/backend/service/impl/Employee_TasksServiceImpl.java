package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee_Tasks;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import at.ac.tuwien.sepm.groupphase.backend.exception.AlreadyHandledException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.Employee_TasksRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaskRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.Employee_TasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;

@Service
public class Employee_TasksServiceImpl implements Employee_TasksService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Employee_TasksRepository employee_tasksRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public Employee_TasksServiceImpl(Employee_TasksRepository employee_tasksRepository, TaskRepository taskRepository) {
        this.employee_tasksRepository = employee_tasksRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Long applyForTask(Employee employee, Task task) {
        LOGGER.debug("Create application from employee {} for task {}", employee.getId(), task.getId());
        Employee_Tasks employee_tasks = new Employee_Tasks();
        employee_tasks.setEmployee(employee);
        employee_tasks.setTask(task);
        if(employee_tasksRepository.findFirstByEmployeeAndTask(employee, task) == null){
            return employee_tasksRepository.save(employee_tasks).getId();
        }else{
            throw new AlreadyHandledException(String.format("Sie haben sich bereits für die Aufgabe \"%s\" beworben", task.getDescription()));
        }
    }

    @Override
    @Transactional
    public void updateStatus(Employee_Tasks employee_tasks) {
        LOGGER.debug("Update status of Application: {}", employee_tasks);
        Employee_Tasks toUpdate = employee_tasksRepository.findFirstByEmployee_Profile_IdAndTask_Id(employee_tasks.getEmployee().getId(), employee_tasks.getTask().getId());
        if(toUpdate == null) throw new NotFoundException("Diese Bewerbung wurde leider bereits zurückgezogen.");
        if(toUpdate.getAccepted() != null){
            String status = toUpdate.getAccepted() ? "akzeptiert" : "abgelehnt";
            throw new AlreadyHandledException(String.format("Die Bewerbung von %s für die Aufgabe %s wurde bereits %s", toUpdate.getEmployee().getProfile().getEmail(), toUpdate.getTask().getDescription(), status));
        }
        Long acceptedEmployees = employee_tasksRepository.countAllByTask_IdAndAccepted(employee_tasks.getTask().getId(), true);
        Task t = taskRepository.getOne(employee_tasks.getTask().getId());
        if(t.getEmployeeCount() > acceptedEmployees) {
            toUpdate.setAccepted(employee_tasks.getAccepted());
            employee_tasksRepository.save(toUpdate);
        } else if(!employee_tasks.getAccepted()){
            toUpdate.setAccepted(employee_tasks.getAccepted());
            employee_tasksRepository.save(toUpdate);
        } else {
            throw new AlreadyHandledException("Diese Aufgabe ist bereits vollständig besetzt.");
        }
    }
}
