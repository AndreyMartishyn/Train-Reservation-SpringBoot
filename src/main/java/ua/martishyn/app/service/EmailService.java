package ua.martishyn.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ua.martishyn.app.entities.User;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender myMailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.welcome.subject}")
    private String subject;

    private static final String IMAGE_ROOT = "src/main/resources/static/images/";
    private static final String MAIL_ROOT = "src/main/resources/static/email/";


    private boolean send(String emailTo, String text) {
        try {
            MimeMessage message = myMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            message.setFrom(username);
            helper.setTo(emailTo);
            helper.setSubject(subject);
            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, "text/html");
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(IMAGE_ROOT + "local_railway.png");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");

            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            myMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Error sending email!", e);
            return false;
        }
        log.info("Email send for customer with email: {} ", emailTo);
        return true;
    }

    public boolean sendWelcomeLetter(User user) {
        String htmlText = readFromFile(MAIL_ROOT + "welcome.html");
        return send(user.getEmail(), htmlText);
    }

    public boolean sendBookingNotification(User user) {
        String htmlText = readFromFile(MAIL_ROOT + "order_made.html");
        return send(user.getEmail(), htmlText);
    }

    public boolean sendOrderPayedConfirmation(User user) {
        String htmlText = readFromFile(MAIL_ROOT + "order_payed.html");
        return send(user.getEmail(), htmlText);
    }

    public boolean sendBookingCancellation(User user) {
        String htmlText = readFromFile(MAIL_ROOT + "order_cancelled.html");
        return send(user.getEmail(), htmlText);
    }

    private String readFromFile(String path) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            try (BufferedReader in = new BufferedReader(new FileReader(path))) {
                String str;
                while ((str = in.readLine()) != null) {
                    contentBuilder.append(str);
                }
            }
        } catch (IOException e) {
            log.error("Reading file error", e);
        }
        return contentBuilder.toString();
    }
}
