package com.codegym.service;

import com.codegym.model.User;
import com.codegym.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface IUserService2 {
    List<UserDto> getUsers();
    UserDto getUserById(Long userId);
    Iterable<UserDto> findAll();
    Optional<UserDto> findById(Long id);
    Optional<UserDto> findByUsername(String username);
    void save(User user);
    void remove(Long id);
    Optional<User> convertUserDtoToUser(UserDto userDto);
}
