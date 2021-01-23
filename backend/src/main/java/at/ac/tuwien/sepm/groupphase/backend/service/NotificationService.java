package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Notification;

import java.util.Set;

public interface NotificationService {

    /**
     * Save a notification
     *
     * @param notification to save
     * @return the notification
     */
    Notification createNotification(Notification notification);

    /**
     * Update a notification
     *
     * @param notification to update
     * @return the updated notification
     */
    Notification updateNotification(Notification notification);

    /**
     * Get all notifications for a recipient
     *
     * @param id of the profile
     * @return Set of all notifications sent to a profile
     */
    Set<Notification> findAllByRecipient_Id(Long id);

    /**
     * Get all applications for an event
     *
     * @param id of the event
     * @return Set of all Notifications with type application for an event
     */
    Set<Notification> findAllApplicationsByEvent_Id(Long id);

    /**
     * Delete a notification
     *
     * @param id to delete
     * @param authorization used for authorizing who can delete the notification
     */
    void deleteNotification(Long id, String authorization);

    /**
     * Change (toggle) favourite of Notification (true <-> false)
     *
     * @param notification to change Boolean favorite
     * @return Notification with changed favourite
     */
    Notification changeFavorite(Notification notification);

    /**
     * Finds the only notification sent by a user regarding an event
     *
     * @param eventId id of event
     * @param employeeId id of the employee who sent the notification
     * @return the notification sent by this employee profile with the given id regarding this event
     */
    Notification findFirstByEvent_IdAndSender_Id(Long eventId, Long employeeId);

    /**
     * Delete an application and its notification
     *
     * @param id of the notification to delete
     * @param authorization used for authorizing who can delete the notification
     */
    void deleteApplication(Long id, String authorization);

    /**
     * Delete an employee from assigned task
     *
     * @param taskId task that employee quit
     * @param authorization token to identify logged in user (employee)
     */
    void deleteEmployeeFromTask(Long taskId, String authorization);
}
