package com.codegym.service;

import com.codegym.model.Notification;
import com.codegym.model.dto.UserDto;

public interface INotificationService {
    Iterable<Notification> findByUser(UserDto userDto);
}
