package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.service.InterestAreaService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(uses = {InterestAreaService.class})
public interface InterestMapper {

    Set<InterestDto> interestToInterestDto(List<Interest> interests);

    Set<InterestDto> interestToInterestDto(Set<Interest> interests);

    Set<Interest> interestDtoToInterest(Set<InterestDto> interestDtos);

    Set<Interest> interestDtoToInterest(List<InterestDto> interestDtos);

    //needed for Set mappers
    @Mapping(source = "interestArea", target = "simpleInterestAreaDto")
    InterestDto interestToInterestDto(Interest interest);

    @Mapping(source = "simpleInterestAreaDto.id", target = "interestArea")
    Interest interestDtoToInterest(InterestDto interestDto);
}
