package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ApplicationStatusDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee_Tasks;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ApplicationStatusMapper {
    @Mapping(source = "employee", target = "employee.id")
    @Mapping(source = "task", target = "task.id")
    Employee_Tasks applicationStatusDtoToEmployee_tasks(ApplicationStatusDto applicationStatusDto);
}
