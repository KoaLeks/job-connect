package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNotificationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NotificationMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Notification;
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
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return notificationMapper.notificationsToSimpleNotificationsDtos(notificationService.findAllByRecipient_Id(profile.getId()));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Get all Notifications of jwt sub Profile", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public void deleteNotification(@PathVariable Long id, @RequestHeader String authorization){
        LOGGER.info("Delete /api/v1/notifications/{}", id);
        notificationService.deleteNotification(id, authorization);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Update Notification of jwt sub Profile", authorizations = {@Authorization(value = "apiKey")})
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public void updateNotification(@RequestBody SimpleNotificationDto simpleNotificationDto, @RequestHeader String authorization){
        LOGGER.info("Update /api/v1/notifications/{}", simpleNotificationDto.getId());
        Profile profile = tokenService.getProfileFromHeader(authorization);
        if((simpleNotificationDto.getSender() != null && !profile.getId().equals(simpleNotificationDto.getSender().getId())) && (simpleNotificationDto.getRecipient() != null && !profile.getId().equals(simpleNotificationDto.getRecipient().getId()))){
            throw new AuthorizationServiceException("Keine Berechtigung um die Nachricht zu verändern!");
        }
        notificationService.updateNotification(notificationMapper.simpleNotificationDtoToNotification(simpleNotificationDto));
    }

    @PutMapping(value = "/changeFavorite")
    @ApiOperation(value = "Change favorite of application", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasAuthority('ROLE_EMPLOYER')")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public SimpleNotificationDto changeFavoriteForApplication(@RequestBody SimpleNotificationDto simpleNotificationDto) {
        LOGGER.info("Update /api/v1/notifications/changeFavorite/{}", simpleNotificationDto.getId());
        return notificationMapper.notificationToSimpleNotificationDto(notificationService.changeFavorite(notificationMapper.simpleNotificationDtoToNotification(simpleNotificationDto)));
    }
}
