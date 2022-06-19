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

    public void send(String emailTo, String subject) {
        try {
            MimeMessage message = myMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            message.setFrom(username);
            helper.setTo(emailTo);
            helper.setSubject(subject);
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = readFromFile("src/main/resources/static/email/welcome.html");
            messageBodyPart.setContent(htmlText, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(
                    "src/main/resources/static/images/local_railway.png");

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);
            myMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("Error sending email!", e);
        }
    }

    public void sendWelcomeLetter(User user) {
        send(user.getEmail(), "Welcome to Local Railways");
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
