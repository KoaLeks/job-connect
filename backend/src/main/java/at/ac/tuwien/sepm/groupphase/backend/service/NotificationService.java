package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Notification;

import java.util.Set;

public interface NotificationService {
    Notification createNotification(Notification notification);

    Set<Notification> getAllByProfileId(Long id);
}
