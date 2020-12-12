package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNotificationDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Notification;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(uses = {EventMapper.class, EventService.class})
public interface NotificationMapper {
    @Named("simpleNotification")
    SimpleNotificationDto notificationToSimpleNotificationDto(Notification notification);

    @IterableMapping(qualifiedByName = "simpleNotification")
    Set<SimpleNotificationDto> notificationsToSimpleNotificationsDtos(Set<Notification> notifications);
}
