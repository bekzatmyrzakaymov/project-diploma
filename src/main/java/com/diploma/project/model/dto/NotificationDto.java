package com.diploma.project.model.dto;

import lombok.Data;

@Data
public class NotificationDto {

    private String notification;
    private Boolean isDone;
    private Long userId;

}
