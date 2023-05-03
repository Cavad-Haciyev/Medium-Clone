package com.example.mediumclone.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public class EmailService {

  @Autowired
  private JavaMailSender javaMailSender;

  public void sendSimpleEmail(String toEmail,
      String body,
      String subject) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("cavad.hcyv@gmail.com");
    message.setTo(toEmail);
    message.setText(body);
    message.setSubject(subject);
    javaMailSender.send(message);


  }
}
