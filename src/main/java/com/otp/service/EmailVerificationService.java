package com.otp.service;

import com.otp.entity.User;
import com.otp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailVerificationService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    static final Map<String,String> emailOtpMapping  = new HashMap<>();

    public Map<String,String> verifyOtp(String email,String otp){
        String storedOtp = emailOtpMapping.get(email);// getting otp from hashmap which was saved
        Map<String,String> response=new HashMap<>();
        if(storedOtp!=null&&storedOtp.equals(otp)){
            //fetch user By email and mark email as verified
          User user=  userService.getUserByEmail(email);
          if(user!=null){
              emailOtpMapping.remove(email);//removing the otp from hashMap
              userService.verifyEmail(user);// set email as true /verified
              response.put("status","success");
              response.put("message","Email verified Successfully");
          }
          else {
              response.put("status","error");
              response.put("message","User NOT found");
          }
        }
        else {
            response.put("status","ERROR");
            response.put("message","Invalid OTP");
        }
        return response;
    }

    public Map<String, String> sendOtpForLogin(String email) {

        if(userService.isEmailVerified(email)){
            String otp = emailService.generateOtp();// get otp and put that otp in HashMap
            emailOtpMapping.put(email,otp);
            //send otp to email
            emailService.sendOtpEmail(email);

            Map<String,String> response= new HashMap<>();
            response.put("status","success");
            response.put("message","OTP sent successfully");
            return response;
        }else{
            Map<String,String> response= new HashMap<>();
            response.put("status","error");
            response.put("message","Email is not verified");
            return response;
        }

    }

    public Map<String, String> verifyOtpForLogin(String email, String otp) {
        String storedOtp = emailOtpMapping.get(email);
        Map<String,String> response=new HashMap<>();
        if(storedOtp!=null && storedOtp.equals(otp)){
            emailOtpMapping.remove(email);// removing the otp so it cannot be used again
            //otp is verified
            response.put("status","success");
            response.put("message","OTP verified successfully");
            return response;
        }
        return response;
    }
}
