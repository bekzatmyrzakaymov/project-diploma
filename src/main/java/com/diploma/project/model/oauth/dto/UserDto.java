package com.diploma.project.model.oauth.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String password;
    private String phoneNumber;
    private String username;
    private String status;
    private LocalDateTime registerDate;
    private Long role;
    private String confirmPassword;
}
