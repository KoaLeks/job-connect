package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestAreaDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface InterestAreaMapper {

    @Named("InterestArea")
    InterestAreaDto interestAreaToInterestAreaDto(InterestArea interestArea);

    InterestArea interestAreaDtoToInterestArea(InterestAreaDto interestAreaDto);
}
