package com.diploma.project.model.oauth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_change_request", schema = "oauth")
@Getter
@Setter
public class PasswordChangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "password_change_request", sequenceName = "SEQ_PASSWD_CHANGE_REQ", allocationSize = 1, schema = "oauth")
    private Long id;

    private String token;

    private String email;

    private LocalDateTime creationDateTime;

    private LocalDateTime expirationDateTime;

}
