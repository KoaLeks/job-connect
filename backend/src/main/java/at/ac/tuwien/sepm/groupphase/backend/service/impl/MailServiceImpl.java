package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import at.ac.tuwien.sepm.groupphase.backend.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EmployeeService employeeService;
    private final ProfileService profileService;

    @Autowired
    public MailServiceImpl(EmployeeService employeeService, ProfileService profileService) {
        this.employeeService = employeeService;
        this.profileService = profileService;
    }

    @Override
    @Transactional
    public void sendNotificationToAvailableEmployees(Event event) {
        LOGGER.debug("sending notification to available employees for event({})", event);
        List<Employee> employeeList = getAvailableEmployees(event);
        for (Employee e: employeeList) {
            sendNotification(event, e);
        }
    }

    @Override
    @Transactional
    public List<Employee> getAvailableEmployees(Event event) {
        return employeeService.getAvailableEmployeesByEvent(event.getId());
    }


    @Override
    public void sendNotification(Event event, Employee employee) {
        mailSender.send(mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setFrom("from@mail.com");
            message.setTo(employee.getProfile().getEmail());
            message.setSubject("Neues Event gefunden!");
            message.setText("<center>" +
                    "<div style='font-family:\"Century Gothic\", sans-serif'>" +
                    "<h1 style='color:#FFA545'>Hi, " + employee.getProfile().getFirstName() + "</h1>" + "<br>" +
                "<p style='font-size: medium'> Es gibt ein neues Event, das dich vielleicht interessieren könnte! Falls du es auschecken willst," +
                " findest du hier die nötigen Infos: " + "<br>"
                    + "Titel: " + event.getTitle() + "<br>" +
                    "Link zur Detailseite: " + "<a href='http://localhost:4200/events/" + event.getId() + "/details'>" +
                    "http://localhost:4200/events/"+ event.getId() + "/details</a></p></center></div>"
                , true);
        });
    }

    @Override
    public void sendMailAboutCanceledEvent(Event event, Set<Employee> employees) {
        for (Employee employee : employees) {
            mailSender.send(mimeMessage -> {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom("from@mail.com");
                message.setTo(employee.getProfile().getEmail());
                message.setSubject("Event wurde abgesagt!");
                message.setText("<center>" +
                        "<div style='font-family:\"Century Gothic\", sans-serif'>" +
                        "<h1 style='color:#FFA545'>Hallo, " + employee.getProfile().getFirstName() + "</h1>" + "<br>" +
                        "<p style='font-size: medium'> Es tut uns sehr leid dich informieren zu müssen, dass das Event: " + event.getTitle() +
                        " am " + event.getStart().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) + " in " + event.getAddress().getState() +
                        " abgesagt wurde." + "<br>" + "Falls du nach einer Alternative suchst, halte hier ausschau: " + "<br>"
                        + "<a href='http://localhost:4200/events'>" + "http://localhost:4200/events" + "</a></p></div></center>"
                    , true);
            });
        }
    }

    @Override
    public void sendContactMail(ContactMessage contactMessage) {
        mailSender.send(mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom("from@mail.com");
            mimeMessageHelper.setTo(this.profileService.findOneById(contactMessage.getTo()).getEmail());
            mimeMessageHelper.setSubject(contactMessage.getSubject());
            mimeMessageHelper.setText(contactMessage.getMessage());
        });
    }

    @Override
    public void sendJobTerminationMail(Task task) {
        Employer employer = task.getEvent().getEmployer();
        mailSender.send(mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setFrom("from@mail.com");
            message.setTo(employer.getProfile().getEmail());
            message.setSubject("Kündigung eines Arbeitnehmers!");
            message.setText("<center>" +
                    "<div style='font-family:'Century Gothic', 'sans-serif'>" +
                    "  <h1 style='color:#FFA545'>Hi, " + (employer.getCompanyName().equals("Privatperson") ? employer.getProfile().getFirstName() : employer.getCompanyName()) + "</h1><br>" +
                    "  <p style='font-size: medium'> Wir müssen dir leider mitteilen, dass ein/e Arbeitnehmer/in den Task <b>"
                    + task.getDescription() + "</b> beim Event <b>" + task.getEvent().getTitle() +
                    "</b> gekündigt hat und die Stelle nun wieder offen ist. Du kannst dir gern aus unserer Liste von " +
                    "ArbeitnehmerInnen passende KandidatInnen raussuchen und kontaktieren unter: " +
                    "<a href='http://localhost:4200/employee-overview'>http://localhost:4200/employee-overview</a>" +
                    "<br></p></center></div>"
                , true);
        });
    }
}
