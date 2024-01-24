package com.codegym.repository;

import com.codegym.model.Notification;
import com.codegym.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationRepository extends JpaRepository<Notification, Long> {
    Iterable<Notification> findByUser(User user);
}
