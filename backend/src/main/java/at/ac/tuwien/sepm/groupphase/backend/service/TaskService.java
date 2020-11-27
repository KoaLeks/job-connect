package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

public interface TaskService {

    /**
     * Saves new task in database.
     *
     * @param task that is being saved to database.
     * @return the newly created task.
     */
    Task saveTask(Task task);
}
