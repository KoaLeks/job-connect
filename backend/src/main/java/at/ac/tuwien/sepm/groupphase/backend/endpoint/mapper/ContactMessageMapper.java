package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ContactMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.ContactMessage;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMessageMapper {

    ContactMessage contactMessageDtoToContactMessage(ContactMessageDto contactMessageDto);

}
