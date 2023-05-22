package com.diploma.project.model.oauth.dto;

import com.diploma.project.model.homePage.ClinicList;
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
    private String city;
    private String address;
    private String photo;
    private ClinicList clinicList;
}
