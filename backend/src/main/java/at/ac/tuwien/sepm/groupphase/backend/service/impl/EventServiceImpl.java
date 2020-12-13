package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Task;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.AddressRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.TaskRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.TaskService;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.lang.invoke.MethodHandles;
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

    @Autowired
    public EventServiceImpl(TaskService taskService, EventRepository eventRepository,
                            AddressRepository addressRepository, MailService mailService,
                            TaskRepository taskRepository) {
        this.taskService = taskService;
        this.eventRepository = eventRepository;
        this.addressRepository = addressRepository;
        this.taskRepository = taskRepository;
        this.mailService = mailService;
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
        mailService.sendNotification(event);
        return savedEvent;
    }

    @Override
    public List<Event> findAll() {
        LOGGER.debug("Find all events");
        return eventRepository.findAll();
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

}
