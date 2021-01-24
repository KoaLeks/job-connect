package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.ContactMessage;
import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;

import java.util.List;
import java.util.Set;

public interface MailService {

    /**
     * Finds all available employees to a specific event. (Time and Interests)
     *
     * @param event event to find employees for
     * @return list of all available employees.
     */
    List<Employee> getAvailableEmployees(Event event);

    /**
     * Sends out an e-mail to an available employee for a specific event
     *
     * @param event event that was created
     * @param employee employee to send mail to
     */
    void sendNotification(Event event, Employee employee);

    /**
     * Iterates over all available employees and sends mail to each of them
     *
     * @param event event that was created
     */
    void sendNotificationToAvailableEmployees(Event event);

    /**
     * Sends an e-mail to all the employees informing them, that the event has been canceled
     *
     * @param event event that has been canceled
     * @param employees that need to be informed
     */
    void sendMailAboutCanceledEvent(Event event, Set<Employee> employees);

    /**
     * Sends an e-mail to the specified employee/r
     *
     * @param contactMessage contains id of the receiver, subject and message of mail
     */
    void sendContactMail(ContactMessage contactMessage);

    /**
     * Sends an e-mail to employer from given task informing them that assigned employee quit
     *
     * @param task task which employee quit and is now available again
     */
    void sendJobTerminationMail(Task task);
}
