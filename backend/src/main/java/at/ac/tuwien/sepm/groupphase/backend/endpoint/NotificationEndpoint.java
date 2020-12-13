package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNotificationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NotificationMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.service.NotificationService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import at.ac.tuwien.sepm.groupphase.backend.service.TokenService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/notifications")
@Validated
public class NotificationEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TokenService tokenService;
    private final ProfileService profileService;
    private final NotificationService notificationService;

    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationEndpoint(TokenService tokenService, ProfileService profileService, NotificationService notificationService, NotificationMapper notificationMapper) {
        this.tokenService = tokenService;
        this.profileService = profileService;
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }

    @GetMapping
    @ApiOperation(value = "Get all Notifications of jwt sub Profile", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public Set<SimpleNotificationDto> getNotifications(@RequestHeader String authorization){
        LOGGER.info("GET /api/v1/notifications");
        String mail = tokenService.getEmailFromHeader(authorization);
        Profile profile = profileService.findProfileByEmail(mail);
        return notificationMapper.notificationsToSimpleNotificationsDtos(notificationService.getAllByProfileId(profile.getId()));
    }
}
