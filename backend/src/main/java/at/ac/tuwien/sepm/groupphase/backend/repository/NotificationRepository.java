package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Set<Notification> findAllByRecipient_Id(Long id);
    Set<Notification> findAllByEvent_Id(Long id);
    Set<Notification> findAllByEvent_IdAndType(Long id, String type);
    Set<Notification> findAllByRecipient_IdAndTypeNot(Long id, String type);
    Notification findFirstByEvent_IdAndSender_Id(Long eventId, Long senderId);
}
