package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

public interface TaskService {
    Task findOneById(Long id);
}
