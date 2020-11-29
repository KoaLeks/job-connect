package at.ac.tuwien.sepm.groupphase.backend.unittests;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AddressMappingTest implements TestData {

    private Address address = Address.AddressBuilder.aAddress()
        .withCity(CITY)
        .withState(STATE)
        .withZip(ZIP)
        .withAddressLine(ADDRESS_LINE)
        .withAdditional(ADDITIONAL)
        .build();

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void givenNothing_whenMapAddressInquiryDtoToEntity_thenEntityHasAllProperties() {
        AddressInquiryDto addressInquiryDto = addressMapper.addressToAddressInquiryDto(address);
        assertAll(
            () -> assertEquals(CITY, addressInquiryDto.getCity()),
            () -> assertEquals(STATE, addressInquiryDto.getState()),
            () -> assertEquals(ZIP, addressInquiryDto.getZip()),
            () -> assertEquals(ADDRESS_LINE, addressInquiryDto.getAddressLine()),
            () -> assertEquals(ADDITIONAL, addressInquiryDto.getAdditional()));

    }

}