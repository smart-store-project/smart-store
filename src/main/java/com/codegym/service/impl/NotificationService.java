package com.codegym.service.impl;

import com.codegym.model.Notification;
import com.codegym.model.dto.UserDto;
import com.codegym.repository.INotificationRepository;
import com.codegym.service.INotificationService;
import com.codegym.service.IUserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private INotificationRepository iNotificationRepository;

    @Autowired
    private IUserService2 userService2;

    @Override
    public Iterable<Notification> findByUser(UserDto userDto) {
        return iNotificationRepository.findByUser(userService2.convertUserDtoToUser(userDto).orElse(null));
    }
}
