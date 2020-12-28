package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Notification;
import at.ac.tuwien.sepm.groupphase.backend.entity.Profile;
import at.ac.tuwien.sepm.groupphase.backend.repository.NotificationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NotificationService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import at.ac.tuwien.sepm.groupphase.backend.service.TokenService;
import at.ac.tuwien.sepm.groupphase.backend.util.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final NotificationRepository notificationRepository;
    private final TokenService tokenService;
    private final ProfileService profileService;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, TokenService tokenService, ProfileService profileService) {
        this.notificationRepository = notificationRepository;
        this.tokenService = tokenService;
        this.profileService = profileService;
    }

    @Override
    public Notification createNotification(Notification notification) {
        LOGGER.debug("Create Notification {}", notification);
        return notificationRepository.save(notification);
    }

    @Override
    public Set<Notification> findAllByRecipient_Id(Long id) {
        LOGGER.debug("Get all notifications from Profile with id: {}", id);
        return notificationRepository.findAllByRecipient_IdAndTypeNot(id, NotificationType.APPLICATION.name());
    }

    @Override
    public Set<Notification> findAllApplicationsByEvent_Id(Long id) {
        LOGGER.debug("Get all applications from Profile with id: {}", id);
        return notificationRepository.findAllByEvent_IdAndType(id, NotificationType.APPLICATION.name());
    }

    @Override
    public Notification updateNotification(Notification notification) {
        LOGGER.debug("Update Notification {}", notification);
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id, String authorization) {
        LOGGER.debug("Delete notification with id: {}", id);

        String mail = tokenService.getEmailFromHeader(authorization);
        Profile profile = profileService.findProfileByEmail(mail);
        Optional<Notification> notification = notificationRepository.findById(id);
        if(!profile.equals(notification.get().getRecipient()))
        {
            throw new AuthorizationServiceException(String.format("No Authorization to delete the Notification: %s", id));
        }
        notificationRepository.deleteById(id);
    }

    @Override
    public Notification changeFavorite(Notification notification) {
        LOGGER.debug("Change favorite of notification with id: {} from {} to {}", notification.getId(), notification.getFavorite(), !notification.getFavorite());
        notification.setFavorite(!notification.getFavorite());
        return notificationRepository.save(notification);
    }


}
