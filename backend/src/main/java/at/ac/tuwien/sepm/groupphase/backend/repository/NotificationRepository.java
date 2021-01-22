package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Finds all notifications sent to a recipient
     *
     * @param id of recipient
     * @return Set of notifications sent to this recipient
     */
    Set<Notification> findAllByRecipient_Id(Long id);

    /**
     * Finds all notifications regarding an event
     *
     * @param id of event
     * @return Set of notifications regarding this event
     */
    Set<Notification> findAllByEvent_Id(Long id);

    /**
     * Finds all notifications of a type regarding a event
     *
     * @param id of event
     * @param type of notification
     * @return Set of notifications of type regarding this event
     */
    Set<Notification> findAllByEvent_IdAndType(Long id, String type);

    /**
     * Finds all notifications not of this type sent to a recipient
     *
     * @param id of recipient
     * @param type of notification, which should not be found
     * @return Set of notifications not of this type sent to this recipient
     */
    Set<Notification> findAllByRecipient_IdAndTypeNot(Long id, String type);

    /**
     * Finds the only notification sent by a user regarding an event
     *
     * @param eventId id of event
     * @param senderId id of sender
     * @return the notification sent by the sender with given id regarding this event
     */
    Notification findFirstByEvent_IdAndSender_Id(Long eventId, Long senderId);

    /**
     * Deletes all notifications of an employer/creator (with given email) of an event referenced in the notification
     *
     * @param email of the employer
     */
    void deleteNotificationsByEvent_Employer_Profile_Email(String email);

    /**
     * Deletes all notifications where either the sender or the recipient owns given email
     *
     * @param email of the user
     * @param sameEmail email of the same user
     */
    void deleteNotificationsByRecipient_EmailEqualsOrSender_EmailEquals(String email, String sameEmail);
}
