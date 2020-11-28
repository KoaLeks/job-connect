package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TaskDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TaskInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface TaskMapper {

    @Named("Task")
    TaskDto taskToTaskDto(Task task);

    Task taskDtoToTask(TaskDto taskDto);

    Task taskInquiryDtoToTask(TaskInquiryDto taskInquiryDto);

    TaskInquiryDto taskToTaskInquiryDto(Task task);

}
