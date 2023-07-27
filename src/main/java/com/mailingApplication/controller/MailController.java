package com.mailingApplication.controller;

import com.mailingApplication.bean.MailBean;
import com.mailingApplication.service.MailService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailService mailService;

    @PostMapping("/sendMail")
    @Retry(name = "send-mail", fallbackMethod = "handleFallBack")
    @CircuitBreaker(name = "send-mail", fallbackMethod = "handleFallBack")
    @RateLimiter(name = "send-mail")
    public ResponseEntity<String> sendMail(@RequestBody MailBean mailBean) throws Exception{
        logger.info("Incoming Request: {}",mailBean.getToMailId());
        return mailService.sendMail(mailBean);
    }

    private ResponseEntity<String> handleFallBack(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
