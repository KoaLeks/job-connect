package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public interface AddressMapper {

    @Named("Address")
    AddressDto addressToAddressDto(Address address);

    Address addressDtoToAddress(AddressDto addressDto);

    Address addressInquiryDtoToAddress(AddressInquiryDto addressInquiryDto);

    AddressInquiryDto addressToAddressInquiryDto(Address address);
}
