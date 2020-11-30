package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.AddressInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.DetailedMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.MessageInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.AddressMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AddressEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private Address address = Address.AddressBuilder.aAddress()
        .withCity(CITY)
        .withState(STATE)
        .withZip(ZIP)
        .withAddressLine(ADDRESS_LINE)
        .withAdditional(ADDITIONAL)
        .build();

    @BeforeEach
    public void beforeEach() {
        addressRepository.deleteAll();
        address = Address.AddressBuilder.aAddress()
            .withCity(CITY)
            .withState(STATE)
            .withZip(ZIP)
            .withAddressLine(ADDRESS_LINE)
            .withAdditional(ADDITIONAL)
            .build();
    }

    @Test
    public void createValidAddressTest() throws Exception {
        String body = objectMapper.writeValueAsString(addressMapper.addressToAddressInquiryDto(address));

        MvcResult mvcResult = this.mockMvc.perform(post(ADDRESSES_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(addressRepository.count(), 1);
    }

    @Test
    public void tryCreateAddressWithoutCity_State_ZIP_AddressLine_ShouldReturnBadRequest() throws Exception {
        address.setCity(null);
        address.setState(null);
        address.setZip(null);
        address.setAddressLine(null);
        String body = objectMapper.writeValueAsString(addressMapper.addressToAddressInquiryDto(address));

        MvcResult mvcResult = this.mockMvc.perform(post(ADDRESSES_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertAll(
            () -> assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus()),
            () -> {
                //Reads the errors from the body
                String content = response.getContentAsString();
                content = content.substring(content.indexOf('[') + 1, content.indexOf(']'));
                String[] errors = content.split(",");
                assertEquals(8, errors.length);
            }
        );

    }

    @Test
    public void tryUpdateAddressToInvalidAddressWithNoZip_ShouldReturnBadRequest() throws Exception {
        addressRepository.save(address);

        address.setZip(null);
        String body = objectMapper.writeValueAsString(addressMapper.addressToAddressInquiryDto(address));

        MvcResult mvcResult = this.mockMvc.perform(put(ADDRESSES_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void tryUpdateCityAndStateOfAddress_thenAddressWithNewCityAndState() throws Exception {
        AddressInquiryDto addressInquiryDto = addressMapper.addressToAddressInquiryDto(address);
        String body = objectMapper.writeValueAsString(addressInquiryDto);

        MvcResult mvcResult = this.mockMvc.perform(post(ADDRESSES_BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        address.setId(1L);
        address.setCity("Wien");
        address.setState("Vienna");
        String updatedBody = objectMapper.writeValueAsString(addressMapper.addressToAddressInquiryDto(address));

        MvcResult mvcUpdateResult = this.mockMvc.perform(put(ADDRESSES_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES))
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedBody))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse updateResponse = mvcUpdateResult.getResponse();

        assertEquals(HttpStatus.OK.value(), updateResponse.getStatus());

        AddressDto addressResponse = objectMapper.readValue(updateResponse.getContentAsString(),
            AddressDto.class);

        assertEquals("Wien", addressResponse.getCity());
        assertEquals("Vienna", addressResponse.getState());
        assertEquals(addressRepository.count(), 1);
    }


}
