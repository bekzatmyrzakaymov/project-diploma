package com.diploma.project.util;

import com.sun.istack.ByteArrayDataSource;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailSenderService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);


    private final JavaMailSender mailSender;
    private final String FILES_ROOT_FOLDER = "/opt/kfm/kgd/materials/";

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("ser.support@afm.gov.kz");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public void sendEmailWithSource(String to, String email, ByteArrayDataSource arrayDataSource,String filename) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(email);
            helper.setFrom("ser.support@afm.gov.kz");
            mimeMessage.setDataHandler(new DataHandler(arrayDataSource));
            mimeMessage.setFileName(filename);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public void sendEmailWithMultipleSource(String comment, List<String> email, List<MultipartFile> files,ByteArrayDataSource arrayDataSource) {
        try {
            String[] to = email.stream().toArray(String[]::new);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            Multipart multipart = new MimeMultipart();
            helper.setText("", true);
            helper.setTo(to);
            helper.setSubject(comment);
            helper.setFrom("ser.support@afm.gov.kz");
            if(!files.isEmpty()){
                for (MultipartFile file:files){
                    DataSource source = new FileDataSource(FILES_ROOT_FOLDER+file.getName());
                    BodyPart messageBodyPart= new MimeBodyPart();
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(file.getName());
                    multipart.addBodyPart(messageBodyPart);
                }
            }
            BodyPart messageBodyPart= new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(arrayDataSource));
            messageBodyPart.setFileName("str.xlsx");
            multipart.addBodyPart(messageBodyPart);
            mimeMessage.setContent(multipart);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public void sendEmailWithZipFile(List<String> email, String path) {
        try {
            String[] to = email.stream().toArray(String[]::new);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            Multipart multipart = new MimeMultipart();
            helper.setText("", true);
            helper.setTo(to);
            helper.setSubject("Форма менее 20 тыс МРП");
            helper.setFrom("ser.support@afm.gov.kz");
            File folder = new File("/opt/kfm/serx/materials/"+path+"/");
            File[] listOfFiles = folder.listFiles();
            // package files
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    DataSource source = new FileDataSource("/opt/kfm/serx/materials/"+path+"/"+file.getName());
                    BodyPart messageBodyPart= new MimeBodyPart();
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(file.getName());
                    multipart.addBodyPart(messageBodyPart);
                }
            }
            mimeMessage.setContent(multipart);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
