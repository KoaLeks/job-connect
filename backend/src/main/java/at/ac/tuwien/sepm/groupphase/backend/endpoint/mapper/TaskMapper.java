package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TaskDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Named("Task")
    TaskDto taskToTaskDto(Task task);

    Task taskDtoToTask(TaskDto taskDto);

    @IterableMapping(qualifiedByName = "Task")
    List<TaskDto> taskstoTaskDtos(List<Task> tasks);

}
