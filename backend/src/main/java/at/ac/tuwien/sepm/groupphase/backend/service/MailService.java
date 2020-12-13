package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

import java.util.List;

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
}
