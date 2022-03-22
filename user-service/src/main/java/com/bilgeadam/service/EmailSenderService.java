package com.bilgeadam.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private JavaMailSender mailSender;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toEmail,String body){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("ba.surveyapp@gmail.com");
        message.setTo(toEmail);
        message.setText("Sayın Öğrenci;\n" +
                "Adınıza anket tanımlanmıştır.\n Bağlantı adresi:\n"+
        "http://18.223.214.79/survey?token="+body);
        message.setSubject("Bilge Adam Anket Servisi");
        mailSender.send(message);


    }

    }

