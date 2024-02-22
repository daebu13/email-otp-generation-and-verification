package com.otp.service;

import com.otp.entity.User;
import com.otp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User userRegistration(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public User getUserByEmail(String email) {
        User userbyEmail = userRepository.findByEmail(email);
        return userbyEmail;
    }

    public void verifyEmail(User user) {
        user.setEmailVerified(true);
        userRepository.save(user);
    }

    public boolean isEmailVerified(String email) {
        User user = userRepository.findByEmail(email);
        return user!=null&&user.isEmailVerified();// returns true
    }
}
