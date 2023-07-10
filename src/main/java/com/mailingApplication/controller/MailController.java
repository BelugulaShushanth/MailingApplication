package com.mailingApplication.controller;

import com.mailingApplication.bean.MailBean;
import com.mailingApplication.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody MailBean mailBean){
        return mailService.sendMail(mailBean);
    }
}
