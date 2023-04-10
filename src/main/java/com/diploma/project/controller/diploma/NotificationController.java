package com.diploma.project.controller.diploma;

import com.diploma.project.model.Notification;
import com.diploma.project.service.impl.NotificationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@Slf4j
public class NotificationController {

    //--Страница - Уведомления
    @Autowired
    private NotificationServiceImpl notificationService;

    @GetMapping("/{id}")
    public List<Notification> get(@PathVariable("id") Long id) {
        log.info("Notification Page - get notification by user id");
        return notificationService.getNotificationByUser(id);
    }

    @PutMapping("/done-notification/{id}")
    public void doneNotification(@PathVariable("id") Long id) {
        log.info("Notification Page - done notification");
         notificationService.doNotification(id);
    }
}
