package com.otp.repository;

import com.otp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
