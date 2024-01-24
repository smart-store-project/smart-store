package com.codegym.service;

import com.codegym.exception.DuplicateUsernameException;
import com.codegym.exception.InvalidUserException;
import com.codegym.model.User;

import java.util.Optional;

public interface IUserService extends IGenerateService<User>{
    boolean isUserExisted(String username);
    Optional<User> findByUsername(String username);
    boolean isValidPassword(String password);
    void addUser(User user) throws DuplicateUsernameException;
    void updateUser(User user) throws InvalidUserException;

}
