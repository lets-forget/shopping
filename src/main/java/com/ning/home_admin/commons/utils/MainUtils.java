package com.ning.home_admin.commons.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MainUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMain( String from, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        //true 表⽰示需要创建⼀一个 multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo("2055569134@qq.com");
        helper.setSubject(subject);
        helper.setText(text, true);
        javaMailSender.send(message);
    }
}
