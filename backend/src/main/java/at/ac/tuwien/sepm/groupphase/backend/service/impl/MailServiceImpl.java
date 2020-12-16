package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.*;
import at.ac.tuwien.sepm.groupphase.backend.service.EmployeeService;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EmployeeService employeeService;

    @Autowired
    public MailServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
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
        LOGGER.debug("get available employees for event({})", event);
        List<Employee> employeeList = employeeService.findAll();
        List<Employee> availableEmployees = new ArrayList<>();

        for (Employee e: employeeList) {
//            System.out.println(e);
            for (Interest i: e.getInterests()) {
                for (Task t: event.getTasks()) {
                    if(i.getInterestArea() != null && i.getInterestArea().getId().equals(t.getInterestArea().getId())){
                        for (Time time: e.getTimes()){
                            if((event.getStart().isAfter(time.getStart()) || event.getStart().isEqual(time.getStart())) &&
                                event.getStart().isBefore(time.getEnd())){
                                availableEmployees.add(e);

                            }
                        }
                    }
                }
            }
        }
//        System.out.println(availableEmployees);

        return availableEmployees;
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
                "<p style='font-size: medium'> Es gibt ein neues Event, das dich vielleicht interessieren könnte! Falls dus auschecken willst," +
                " findest du hier die nötigen Infos: " + "<br>"
                    + "Titel: " + event.getTitle() + "<br>" +
                    "Link zur Detailseite: " + "<a href='http://localhost:4200/events/" + event.getId() + "/details'>" +
                    "http://localhost:4200/events/"+ event.getId() + "/details</a></p></center></div>"
                , true);
        });
    }
}
