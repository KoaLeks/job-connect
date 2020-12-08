package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TimeDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Time;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(uses = {EmployeeService.class})
public interface TimeMapper {

    Set<TimeDto> ToTimeDtoSet(Set<Time> times);

    Set<Time> toTimeSet(Set<TimeDto> timeDtos);

    @Mapping(source = "employee.id", target = "employee_ID")
    TimeDto timeToTimeDto(Time time);

    @Mapping(source = "employee_ID", target = "employee")
    Time timeDtoToTime(TimeDto timeDto);
}
