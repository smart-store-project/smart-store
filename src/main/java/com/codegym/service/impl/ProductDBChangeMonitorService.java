package com.codegym.service.impl;

import com.codegym.model.Notification;
import com.codegym.model.Order;
import com.codegym.model.OrderStatus;
import com.codegym.repository.INotificationRepository;
import com.codegym.repository.IOrderRepository;
import com.codegym.repository.IOrderStatusRepository;
import com.codegym.repository.IProductRepository;
import com.codegym.service.IProductDBChangeMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ProductDBChangeMonitorService implements IProductDBChangeMonitorService {
    @Autowired
    private IOrderRepository iOrderRepository;

    @Autowired
    private INotificationRepository iNotificationRepository;

    private final Map<Long, String> previousValues = new HashMap<>();

    private final AtomicBoolean firstExecution = new AtomicBoolean(true);


    @Override
    @Scheduled(fixedDelay = 5000)
    public void checkForChanges() {
        if (firstExecution.getAndSet(false)) {
            Iterable<Order> entities = iOrderRepository.findAll();
            entities.forEach(entity -> {
                previousValues.put(entity.getId(), entity.getOrderStatus().getName());
            });
            return;
        }
        Iterable<Order> entities = iOrderRepository.findAll();
        entities.forEach(entity -> {
            String currentAttributeValue = entity.getOrderStatus().getName();
            String previousAttributeValue = previousValues.get(entity.getId());
            if (previousAttributeValue == null || !previousAttributeValue.equals(currentAttributeValue)) {
                System.out.println("Attribute value changed: " + currentAttributeValue);
                previousValues.put(entity.getId(), currentAttributeValue);
                Notification notification = new Notification();
                notification.setDate(Timestamp.from(Instant.now()));
                if (previousAttributeValue != null) {
                    notification.setTitle("Change status");
                    notification.setDescription("Order change status from " + previousAttributeValue + " to " + currentAttributeValue);
                } else {
                    notification.setTitle("Create new order");
                    notification.setDescription("You have just created new order. Your order status is " + currentAttributeValue);
                }

                notification.setUser(entity.getUser());
                iNotificationRepository.save(notification);
            }

        });
    }
}
