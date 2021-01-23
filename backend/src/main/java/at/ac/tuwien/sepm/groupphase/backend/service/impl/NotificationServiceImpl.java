package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.Employee_TasksRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.NotificationRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import at.ac.tuwien.sepm.groupphase.backend.util.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final NotificationRepository notificationRepository;
    private final Employee_TasksRepository employee_tasksRepository;
    private final TokenService tokenService;
    private final ProfileService profileService;
    private final MailService mailService;
    private final TaskService taskService;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, TokenService tokenService,
                                   ProfileService profileService, MailService mailService, TaskService taskService,
                                   Employee_TasksRepository employee_tasksRepository) {
        this.notificationRepository = notificationRepository;
        this.tokenService = tokenService;
        this.profileService = profileService;
        this.employee_tasksRepository = employee_tasksRepository;
        this.mailService = mailService;
        this.taskService = taskService;
    }

    @Override
    public Notification createNotification(Notification notification) {
        LOGGER.debug("Create Notification {}", notification);
        return notificationRepository.save(notification);
    }

    @Override
    public Set<Notification> findAllByRecipient_Id(Long id) {
        LOGGER.debug("Get all notifications from Profile with id: {}", id);
        return notificationRepository.findAllByRecipient_IdAndTypeNot(id, NotificationType.APPLICATION.name());
    }

    @Override
    public Set<Notification> findAllApplicationsByEvent_Id(Long id) {
        LOGGER.debug("Get all applications from Profile with id: {}", id);
        return notificationRepository.findAllByEvent_IdAndType(id, NotificationType.APPLICATION.name());
    }

    @Override
    public Notification updateNotification(Notification notification) {
        LOGGER.debug("Update Notification {}", notification);
        Long id = notification.getId();
        if(!notificationRepository.existsById(id)) {
            throw new NotFoundException("Diese Bewerbung wurde leider bereits zurückgezogen.");
        }
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id, String authorization) {
        LOGGER.debug("Delete notification with id: {}", id);

        String mail = tokenService.getEmailFromHeader(authorization);
        Profile profile = profileService.findProfileByEmail(mail);
        Optional<Notification> notification = notificationRepository.findById(id);
        if(notification.isPresent()) {
            if(!profile.equals(notification.get().getRecipient()))
            {
                throw new AuthorizationServiceException(String.format("No Authorization to delete the Notification: %s", id));
            }
            notificationRepository.deleteById(id);
        }
    }

    @Override
    public Notification changeFavorite(Notification notification) {
        LOGGER.debug("Change favorite of notification with id: {} from {} to {}", notification.getId(), notification.getFavorite(), !notification.getFavorite());
        Long id = notification.getId();
        if(!notificationRepository.existsById(id)) {
            throw new NotFoundException("Diese Bewerbung wurde leider bereits zurückgezogen.");
        }
        notification.setFavorite(!notification.getFavorite());
        return notificationRepository.save(notification);
    }

    @Override
    public Notification findFirstByEvent_IdAndSender_Id(Long eventId, Long employeeId) {
        LOGGER.debug("Find application from Employee {} with Event id {} ", employeeId, eventId);
        return notificationRepository.findFirstByEvent_IdAndSender_Id(eventId, employeeId);
    }

    @Override
    @Transactional
    public void deleteApplication(Long id, String authorization) {
        LOGGER.debug("Delete application with id: {}", id);

        String mail = tokenService.getEmailFromHeader(authorization);
        Profile profile = profileService.findProfileByEmail(mail);
        Optional<Notification> notification = notificationRepository.findById(id);
        if(notification.isPresent()) {
            if(!profile.equals(notification.get().getSender()))
            {
                throw new AuthorizationServiceException(String.format("No Authorization to delete the Notification: %s", id));
            }
            //deletes the application
            if(notificationRepository.existsById(id)) {
                notificationRepository.deleteById(id);
            }
            //deletes the corresponding notification for the employer
            if(notificationRepository.existsById(id + 1)) {
                notificationRepository.deleteById(id + 1);
            }
            Long taskId = notification.get().getTask().getId();
            employee_tasksRepository.deleteEmployee_TaskByEmployee_Profile_Email_AndTask_Id(mail, taskId);
        }
    }
    @Override
    @Transactional
    public void deleteEmployeeFromTask(Long taskid, String authorization) {
        LOGGER.debug("Delete job with id: {}", taskid);
        String mail = tokenService.getEmailFromHeader(authorization);
        employee_tasksRepository.deleteEmployee_TaskByEmployee_Profile_Email_AndTask_Id(mail, taskid);
        notificationRepository.deleteNotificationByTask_IdAndTypeAndRecipient_Email(taskid, NotificationType.EVENT_ACCEPTED.name(), mail);

        Task task = this.taskService.findOneById(taskid);
        this.mailService.sendJobTerminationMail(task);
    }
}

