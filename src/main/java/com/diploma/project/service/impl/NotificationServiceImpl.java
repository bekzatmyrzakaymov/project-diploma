package com.diploma.project.service.impl;

import com.diploma.project.model.Notification;
import com.diploma.project.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getNotificationByUser(Long id){
        return notificationRepository.findAllByUserId(id);
    }
}
