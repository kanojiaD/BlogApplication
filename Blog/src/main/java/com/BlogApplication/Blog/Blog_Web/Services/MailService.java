package com.BlogApplication.Blog.Blog_Web.Services;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    private String Message(String otp)
    {
        return
                "<p>You're receiving this e-mail because you or someone else has requested a password reset for your user account at</p>" +
                "<h1 style='color:tomato;'>" + otp + "</h1>" +
                "<p> is the one time password to reset password</p>" +
                "<p>If you did not request a password reset you can safely ignore this email.</p>" ;
    }
    public void sendMail(String email,String otp)
    {
        String subject="[Blog Application] Password Reset E-Mail";
        String message = Message(otp);
        String to = email;
        String from = "mail.blogapplication@gmail.com";
        mail(subject, message, to, from);
    }
    private void mail(String subject, String message, String to, String from)
    {
        String host= "smtp.gmail.com";
        Properties properties= System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");


        Session session= Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "#b1BlogApplication");
            }
        });
        MimeMessage mimeMessage= new MimeMessage(session);
        try{
            mimeMessage.setFrom(from);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(message, "text/html; charset=utf-8");
            Transport.send(mimeMessage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
