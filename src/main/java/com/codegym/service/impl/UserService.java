package com.codegym.service.impl;

import com.codegym.exception.DuplicateUsernameException;
import com.codegym.exception.InvalidUserException;
import com.codegym.model.User;
import com.codegym.repository.IUserRepository;
import com.codegym.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Iterable<User> findAll() {
        return iUserRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return iUserRepository.findById(id);
    }

    @Override
    public void save(User user) {
        iUserRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        iUserRepository.deleteById(id);
    }

    @Override
    public boolean isUserExisted(String username) {
        return findByUsername(username).isPresent();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return iUserRepository.findByUsernameOrEmailOrPhoneNumber(username, username, username);
    }

    @Override
    public boolean isValidPassword(String password) {
        return password.trim().length() > 7 &&  password.trim().length() < 31;
    }

    @Override
    public void addUser(User user) throws DuplicateUsernameException {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            iUserRepository.save(user);
        } else {
            throw new DuplicateUsernameException();
        }
    }

    @Override
    public void updateUser(User user)throws InvalidUserException {
        if (findById(user.getId()).isPresent()) {
            iUserRepository.save(user);
        } else {
            throw new InvalidUserException();
        }
    }
}
