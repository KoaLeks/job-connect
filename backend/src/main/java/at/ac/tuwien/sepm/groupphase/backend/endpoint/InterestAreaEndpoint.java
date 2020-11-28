package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.InterestAreaDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleMessageDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.InterestAreaMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.InterestAreaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/interestareas")
public class InterestAreaEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final InterestAreaService interestAreaService;
    private final InterestAreaMapper interestAreaMapper;

    @Autowired
    public InterestAreaEndpoint(InterestAreaMapper interestAreaMapper, InterestAreaService interestAreaService) {
        this.interestAreaMapper = interestAreaMapper;
        this.interestAreaService = interestAreaService;
    }

    @GetMapping
    @ApiOperation(value = "Get list of InterestAreas", authorizations = {@Authorization(value = "apiKey")})
    public List<InterestAreaDto> findAll() {
        LOGGER.info("GET /api/v1/interestareas");
        return interestAreaMapper.interestAreaToInterestAreaDto(interestAreaService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Publish a new InterestArea", authorizations = {@Authorization(value = "apiKey")})
    public InterestAreaDto create(@Valid @RequestBody InterestAreaDto interestAreaDto) {
        LOGGER.info("POST /api/v1/interestareas/{}", interestAreaDto);

        return interestAreaMapper.interestAreaToInterestAreaDto(
            interestAreaService.saveInterestArea(interestAreaMapper.interestAreaDtoToInterestArea(interestAreaDto)));
    }


}
