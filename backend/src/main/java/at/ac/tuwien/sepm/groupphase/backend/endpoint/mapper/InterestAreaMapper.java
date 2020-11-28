package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestAreaDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
import at.ac.tuwien.sepm.groupphase.backend.entity.Message;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface InterestAreaMapper {

    @Named("InterestArea")
    InterestAreaDto interestAreaToInterestAreaDto(InterestArea interestArea);

    InterestArea interestAreaDtoToInterestArea(InterestAreaDto interestAreaDto);

    List<InterestAreaDto> interestAreaToInterestAreaDto(List<InterestArea> interestArea);
}
