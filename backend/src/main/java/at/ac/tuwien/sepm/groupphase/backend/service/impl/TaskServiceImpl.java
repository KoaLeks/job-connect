package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaskRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task findOneById(Long id) {
        LOGGER.debug("Find task by id: {}", id);
        Optional<Task> task = taskRepository.findById(id);
        if(task.isPresent()){
            return task.get();
        }else{
            throw new NotFoundException(String.format("Could not find Task with id %s", id));
        }
    }

    @Override
    public Long updateTask(Task task) {
        LOGGER.debug("Update task: {}", task);
        return taskRepository.save(task).getId();
    }
}
