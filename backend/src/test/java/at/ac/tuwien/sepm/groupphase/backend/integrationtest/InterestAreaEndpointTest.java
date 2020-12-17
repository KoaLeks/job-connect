package at.ac.tuwien.sepm.groupphase.backend.integrationtest;

import at.ac.tuwien.sepm.groupphase.backend.basetest.TestData;
import at.ac.tuwien.sepm.groupphase.backend.config.properties.SecurityProperties;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestAreaDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InterestAreaMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class InterestAreaEndpointTest implements TestData {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InterestAreaRepository interestAreaRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InterestAreaMapper interestAreaMapper;

    @Autowired
    private JwtTokenizer jwtTokenizer;

    @Autowired
    private SecurityProperties securityProperties;

    private InterestArea interestArea = InterestArea.InterestAreaBuilder.aInterest()
        .withArea(AREA)
        .withDescription(DESCRIPTION)
        .withInterests(INTERESTS)
        .withTasks(TASKS)
        .build();

    @BeforeEach
    public void beforeEach() {
        interestRepository.deleteAll();
        interestAreaRepository.deleteAll();
        interestArea = InterestArea.InterestAreaBuilder.aInterest()
            .withArea(AREA)
            .withDescription(DESCRIPTION)
            .withInterests(INTERESTS)
            .withTasks(TASKS)
            .build();
    }

    @Test
    public void givenOneInterestArea_whenFindAll_thenListWithSizeOneAndInterestAreaWithAllProperties()
        throws Exception {
        interestAreaRepository.save(interestArea);

        MvcResult mvcResult = this.mockMvc.perform(get(INTERESTAREAS_BASE_URI)
            .header(securityProperties.getAuthHeader(), jwtTokenizer.getAuthToken(ADMIN_USER, ADMIN_ROLES)))
            .andDo(print())
            .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        List<InterestAreaDto> interestAreaDtos = Arrays.asList(objectMapper.readValue(response.getContentAsString(),
            InterestAreaDto[].class));

        assertEquals(1, interestAreaDtos.size());
        InterestAreaDto interestAreaDto = interestAreaDtos.get(0);
        assertAll(
            () -> assertEquals(interestArea.getId(), interestAreaDto.getId()),
            () -> assertEquals(AREA, interestAreaDto.getArea()),
            () -> assertEquals(DESCRIPTION, interestAreaDto.getDescription()),
            () -> assertEquals(INTERESTS, interestAreaDto.getInterests()),
            () -> assertEquals(TASKS, interestAreaDto.getTasks())
        );
    }


}
