package com.otp.controller;

import com.otp.entity.User;
import com.otp.repository.UserRepository;
import com.otp.service.EmailService;
import com.otp.service.EmailVerificationService;
import com.otp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Registration {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailVerificationService emailVerificationService;
    @PostMapping("/register")
    public Map<String,String> registerUser(@RequestBody User user){
        if(userRepository.existsByEmail(user.getEmail())){
            Map<String,String> respone= new HashMap<>();
            respone.put("message","Email already registered .Try another email");
            return  respone;
        }

        User registeredUser=userService.userRegistration(user);
        //send otp to email for email verification
        emailService.sendOtpEmail(user.getEmail());

        // send  a response back
        Map<String,String> response = new HashMap<>();
        response.put("status","success");
        response.put("message","User registered successfully.Check your email for verification");
        return response;

    }
    @PostMapping("/verify-otp")
    public Map<String,String> verifyOtp(@RequestParam String email,@RequestParam String otp){
        return emailVerificationService.verifyOtp(email,otp);
    }
}
