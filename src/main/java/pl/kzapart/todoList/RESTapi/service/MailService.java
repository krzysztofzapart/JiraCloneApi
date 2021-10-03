package pl.kzapart.todoList.RESTapi.service;

import com.sun.mail.util.MailConnectException;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import pl.kzapart.todoList.RESTapi.model.NotificationEmail;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MailBuilder mailBuilder;

    public void sendMail(NotificationEmail notificationEmail)
    {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("verify@kzapart.pl");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailBuilder.build(notificationEmail.getBody()));
        };
        try{
            mailSender.send(messagePreparator);
        }catch (MailException e)
        {
            throw new IllegalStateException("Exeption occured while sending mail");
        }
    }
}
