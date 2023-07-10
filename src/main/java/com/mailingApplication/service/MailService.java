package com.mailingApplication.service;

import com.mailingApplication.bean.MailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    public String sendMail(MailBean mailBean){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@cricfizz.com");
        mailMessage.setTo(mailBean.getToMailId());
        mailMessage.setSubject(mailBean.getSubject());
        mailMessage.setText(mailBean.getBody());
        try {
            javaMailSender.send(mailMessage);
            logger.info("Mail Sent Successfully TO: {}",mailBean.getToMailId());
            return "Mail Sent Successfully To "+mailBean.getToMailId();
        }
        catch (Exception e){
            logger.error("Unable To Send Mail To: Exception:{}",mailBean.getToMailId(),e);
            return "Unable To Send Mail To "+mailBean.getToMailId();
        }

    }
}
