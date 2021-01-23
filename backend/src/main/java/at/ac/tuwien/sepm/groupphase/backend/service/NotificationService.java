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
     * @return list of all notifications from a profile
     */
    Set<Notification> findAllByRecipient_Id(Long id);

    /**
     * Get all applications for an event
     *
     * @param id of the event
     * @return list of all applications for an event
     */
    Set<Notification> findAllApplicationsByEvent_Id(Long id);

    /**
     * Delete a notification
     *
     * @param id to delete
     */
    void deleteNotification(Long id, String authorization);

    /**
     * Change (toggle) favorite of Notification (true <-> false)
     *
     * @param notification to change Boolean favorite
     */
    Notification changeFavorite(Notification notification);

    Notification findFirstByEvent_IdAndSender_Id(Long eventId, Long employeeId);

    /**
     * Delete an application and its notification
     *
     * @param id to delete
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
