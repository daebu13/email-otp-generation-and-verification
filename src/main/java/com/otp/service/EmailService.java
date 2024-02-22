package com.otp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.otp.service.EmailVerificationService.emailOtpMapping;


@Service
public class EmailService {

    private UserService userService;
    private JavaMailSender javaMailSender;

    public EmailService(UserService userService,JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender=javaMailSender;
    }
    
    public String generateOtp(){
        return String.format("%06d",new java.util.Random().nextInt(1000000));
    }
    
    public void  sendOtpEmail(String email){
        String otp = generateOtp();
        
        //save this otp for later verification
        emailOtpMapping.put(email,otp);
        sendEmail(email,"Otp for email verification","Your OTP is"+ otp);



    }


    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

}
