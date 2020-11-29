package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.AddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(value = "/api/v1/addresses")
public class AddressEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressEndpoint(AddressMapper addressMapper, AddressService addressService) {
        this.addressMapper = addressMapper;
        this.addressService = addressService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Publish a new address", authorizations = {@Authorization(value = "apiKey")})
    public AddressDto create(@Valid @RequestBody AddressInquiryDto addressDto) {
        LOGGER.info("POST /api/v1/addresses/{}", addressDto);

        return addressMapper.addressToAddressDto(
            addressService.saveAddress(addressMapper.addressInquiryDtoToAddress(addressDto)));

    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update an address", authorizations = {@Authorization(value = "apiKey")})
    public AddressDto update(@Valid @RequestBody AddressInquiryDto addressDto) {
        LOGGER.info("PUT /api/v1/addresses/{}", addressDto);

        return addressMapper.addressToAddressDto(
            addressService.saveAddress(addressMapper.addressInquiryDtoToAddress(addressDto)));

    }


}
