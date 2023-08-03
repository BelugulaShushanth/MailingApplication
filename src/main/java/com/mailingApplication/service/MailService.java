package com.mailingApplication.service;

import com.mailingApplication.bean.MailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    public ResponseEntity<String> sendMail(MailBean mailBean) throws Exception{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
        messageHelper.setFrom("noreply@cricfizz.com");
        messageHelper.setTo(mailBean.getToMailId());
        messageHelper.setSubject(mailBean.getSubject());
        messageHelper.setText(mailBean.getBody(),true);
        try {
            javaMailSender.send(mimeMessage);
            logger.info("Mail Sent Successfully TO: {}",mailBean.getToMailId());
            return ResponseEntity.ok("Mail Sent Successfully To "+mailBean.getToMailId());
        }
        catch (Exception e){
            logger.error("Unable To Send Mail To: Exception:{} {}",mailBean.getToMailId(),e.getMessage());
            throw new Exception("Unable To Send Mail To: "+mailBean.getToMailId()+"  please retry again");
        }
    }
}
