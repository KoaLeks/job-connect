package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InterestMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.InterestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/interests")
public class InterestEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InterestMapper interestMapper;
    private final InterestService interestService;

    @Autowired
    public InterestEndpoint(InterestMapper interestMapper, InterestService interestService) {
        this.interestMapper = interestMapper;
        this.interestService = interestService;
    }

    @GetMapping
    @ApiOperation(value = "Get list of Interests", authorizations = {@Authorization(value = "apiKey")})
    public Set<InterestDto> findAll() {
        LOGGER.info("GET /api/v1/interests");
        return interestMapper.interestToInterestDto(interestService.findAll());
    }

}
