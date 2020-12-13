package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.Employee;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;




    @Override
    public void sendNotificationToAvailableEmployees() {

    }

    @Override
    public List<Employee> getAvailableEmployees() {
        return null;
    }


    @Override
    public void sendNotification(Event event) {
        mailSender.send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom("from@mail.com");
                message.setTo("");
                message.setSubject("Neues Event gefunden!");
                message.setText("<center>" +
                        "<div style='font-family:\"Century Gothic\", sans-serif'>" +
                        "<h1 style='color:#FFA545'>Hi, " + "Stefo!" + "</h1>" + "<br>" +
                    "<p style='font-size: medium'> Es gibt ein neues Event, das dich vielleicht interessieren könnte! Falls dus auschecken willst," +
                    " findest du hier die nötigen Infos: " + "<br>"
                        + "Titel: " + event.getTitle() + "<br>" +
                        "Link zur Detailseite: " + "<a href='http://localhost:4200/events/" + event.getId() + "/details'>" +
                        "http://localhost:4200/events/</a></p></center></div>"
                    , true);
            }
        });
    }


}
