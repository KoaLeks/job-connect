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

    Set<InterestDto> interestListToInterestDtoSet(List<Interest> interests);

    Set<InterestDto> interestSetToInterestDtoSet(Set<Interest> interests);

    Set<Interest> interestDtoSetToInterestSet(Set<InterestDto> interestDtos);

    Set<Interest> interestDtoListToInterestSet(List<InterestDto> interestDtos);

    //needed for Set mappers
    @Mapping(source = "interestArea", target = "simpleInterestAreaDto")
    InterestDto interestToInterestDto(Interest interest);

    @Mapping(source = "simpleInterestAreaDto.id", target = "interestArea")
    Interest interestDtoToInterest(InterestDto interestDto);
}
