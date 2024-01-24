package com.codegym.repository;

import com.codegym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);
}
