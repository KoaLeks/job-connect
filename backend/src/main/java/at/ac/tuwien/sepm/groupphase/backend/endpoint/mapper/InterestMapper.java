package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper
public interface InterestMapper {

    @Named("Interest")
    InterestDto interestToInterestDto(Interest interest);

    Interest interestDtoToInterest(InterestDto interestDto);

    Set<InterestDto> interestToInterestDto(List<Interest> interests);

    Set<InterestDto> interestToInterestDto(Set<Interest> interests);

    Set<Interest> interestDtoToInterest(Set<InterestDto> interestDtos);

    Set<Interest> interestDtoToInterest(List<InterestDto> interestDtos);
}
