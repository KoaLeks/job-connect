package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NotificationRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaskRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.TaskService;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TaskService taskService;
    private final EventRepository eventRepository;
    private final AddressRepository addressRepository;
    private final TaskRepository taskRepository;
    private final MailService mailService;
    private final NotificationRepository notificationRepository;

    @Autowired
    public EventServiceImpl(TaskService taskService, EventRepository eventRepository,
                            AddressRepository addressRepository,
                            TaskRepository taskRepository,
                            MailService mailService, NotificationRepository notificationRepository) {
        this.taskService = taskService;
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
        this.taskRepository = taskRepository;
        this.mailService = mailService;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Event saveEvent(Event event) throws ValidationException {
        LOGGER.debug("save event({})", event);
        if(event.getAddress() != null) {
            event.setAddress(addressRepository.save(event.getAddress()));
        }
        Event savedEvent = eventRepository.save(event);
        if(event.getTasks() != null) {
            Set<Task> tasks = event.getTasks();
            for (Task task : tasks) {
                task.setEvent(savedEvent);
                taskRepository.save(task);
            }
        }
        mailService.sendNotificationToAvailableEmployees(event);
        return savedEvent;
    }

    @Override
    public List<Event> findAll(Specification<Event> eventSpecification) {
        LOGGER.debug("Find events");
        return eventRepository.findAll(eventSpecification);
    }

    @Override
    public Event findById(Long id) {
        LOGGER.debug("Find event with id {}", id);
        if(id != null) {
            Optional<Event> event = eventRepository.findById(id);
            if (event.isPresent()) return event.get();
            else throw new NotFoundException(String.format("Could not find event with id %s", id));
        }
        return null;
    }

    @Override
    public Event findByTask(Task task) {
        LOGGER.debug("Find event by Task task: {}", task);
        Event event = eventRepository.findFirstByTasks(task);
        if(event != null) return event;
        else throw new NotFoundException(String.format("Could not find Event from given Task %s", task));
    }

    @Override
    @Transactional
    public void deleteEventById(Long id) {
        LOGGER.debug("Delete event by Id: {}", id);
        Event event = eventRepository.getOne(id);
        Set<Employee> employeeSet = new HashSet<>();
        Set<Task> tasks = event.getTasks();
        for (Task t : tasks) {
            Set<Employee_Tasks> employee_tasks = t.getEmployees();
            for (Employee_Tasks e : employee_tasks) {
                employeeSet.add(e.getEmployee());
            }
        }
        Set<Notification> notifications = notificationRepository.findAllByEvent_Id(id);
        notificationRepository.deleteAll(notifications);
        eventRepository.deleteById(id);
        new Thread(() -> {
            mailService.sendMailAboutCanceledEvent(event, employeeSet);
        }).start();
    }

}
