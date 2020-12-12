package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;

import java.util.List;

public interface MailService {
    List<Employee> getAvailableEmployees();
    void sendNotification(Event event);
    void sendNotificationToAvailableEmployees();
}
