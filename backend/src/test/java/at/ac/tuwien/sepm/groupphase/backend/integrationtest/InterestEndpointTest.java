package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Interest;
import at.ac.tuwien.sepm.groupphase.backend.entity.InterestArea;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestAreaRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.InterestRepository;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class InterestEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    InterestRepository interestRepository;

    @Autowired
    private InterestAreaRepository interestAreaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private final InterestArea interestArea = InterestArea.InterestAreaBuilder.aInterest()
        .withArea(AREA)
        .withDescription(DESCRIPTION)
        .withInterests(INTERESTS)
        .withTasks(TASKS)
        .build();

    @BeforeEach
    public void beforeEach() {
        interestAreaRepository.deleteAll();
        interestRepository.deleteAll();
    }

    @Test
    public void givenOneInterest_whenFindAll_thenListWithSizeOneAndInterestWithAllProperties()
        throws Exception {
        interestAreaRepository.save(interestArea);

        Interest interest = Interest.InterestBuilder.aInterest()
            .withId(INTEREST_ID)
            .withName(INTEREST_NAME)
            .withDescription(INTEREST_DESCRIPTION)
            .withInterestArea(interestArea)
            .withEmployees(INTEREST_EMPLOYEES)
            .build();

        interestRepository.save(interest);

        MvcResult mvcResult = this.mockMvc.perform(get(GET_INTERESTS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<InterestDto> interestDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            InterestDto[].class));

        assertEquals(1, interestDtos.size());
        InterestDto interestDto = interestDtos.get(0);
        assertAll(
            () -> assertEquals(INTEREST_ID, interestDto.getId()),
            () -> assertEquals(INTEREST_NAME, interestDto.getName()),
            () -> assertEquals(INTEREST_DESCRIPTION, interestDto.getDescription())
        );
    }
}
