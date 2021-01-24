package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

public interface TaskService {


    /**
     * Find a task by id
     *
     * @param id to look for
     * @return the task
     */
    Task findOneById(Long id);

    /**
     * Update a task
     *
     * @param task to update
     * @return the task id
     */
    Long updateTask(Task task);
}
