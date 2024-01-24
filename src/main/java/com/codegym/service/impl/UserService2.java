package com.codegym.service.impl;

import com.codegym.model.User;
import com.codegym.model.dto.UserDto;
import com.codegym.repository.IUserRepository;
import com.codegym.service.IUserService2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService2 implements IUserService2 {
    private final IUserRepository iUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService2(IUserRepository iUserRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public Iterable<UserDto> findAll() {
        Iterable<User> entities = iUserRepository.findAll();
        return StreamSupport.stream(entities.spliterator(), true)
                .map(entity -> modelMapper.map(entity, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> user = iUserRepository.findById(id);
        return Optional.ofNullable(modelMapper.map(user, UserDto.class));
    }

    @Override
    public void save(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        iUserRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        iUserRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = iUserRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = iUserRepository.findById(userId).orElse(null);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Optional<UserDto> findByUsername(String username) {
        Optional<User> user = iUserRepository.findByUsernameOrEmailOrPhoneNumber(username, username, username);
        return Optional.ofNullable(modelMapper.map(user.get(), UserDto.class));
    }

    @Override
    public Optional<User> convertUserDtoToUser(UserDto userDto) {
        return iUserRepository.findByUsernameOrEmailOrPhoneNumber(userDto.getUsername(), userDto.getEmail(), userDto.getPhoneNumber());
    }
}
