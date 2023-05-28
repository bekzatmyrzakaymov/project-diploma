package com.diploma.project.service.impl;

import com.diploma.project.model.Notification;
import com.diploma.project.model.dto.NotificationDto;
import com.diploma.project.model.oauth.User;
import com.diploma.project.repository.NotificationRepository;
import com.diploma.project.repository.oauth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String createNotification(NotificationDto notificationDto){
        Notification notification = new Notification();
        if(notificationDto.getNotification()!=null){
            notification.setNotification(notificationDto.getNotification());
        }
        if(notificationDto.getIsDone()!=null){
            notification.setIsDone(notificationDto.getIsDone());
        }
        if(notificationDto.getUserId()!=null){
            Optional<User> userOptional = userRepository.findById(notificationDto.getUserId());
            if(userOptional.isPresent()){
                notification.setUser(userOptional.get());
            }
        }
        notificationRepository.save(notification);
        return "Успешно";
    }

    public List<Notification> getNotificationByUser(Long id){
        return notificationRepository.findAllByUserId(id);
    }

    public void doNotification(Long id){
        notificationRepository.notificationDone(id);
    }
}
