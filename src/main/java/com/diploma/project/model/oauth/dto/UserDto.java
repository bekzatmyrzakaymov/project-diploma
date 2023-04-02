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
    private String iin;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String password;
    private String phoneNumber;
    private String username;
    private Long structuralSubdivision;
    private Long position;
    private Long role;
    private Long accessRegion;
    private String status;
    private LocalDateTime registerDate;
    private LocalDateTime activationDate;
    private Boolean hasDeletePermission;
    private Long userSignedDocument;
    private String confirmPassword;
    private Boolean userAgreement;
}
