package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

import java.util.List;

public interface TaskService {

    /**
     * Saves new task in database.
     *
     * @param task that is being saved to database.
     * @return the newly created task.
     */
    Task saveTask(Task task);

    /**
     * Find all tasks entries.
     *
     * @return list of all tasks entries
     */
    List<Task> findAll();
}
