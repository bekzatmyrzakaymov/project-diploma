package com.diploma.project.service.oauth.service;

public interface PasswordRecoveryService {

    String forgotPassword(String email);

    Boolean resetPassword(String token);

    Boolean restorePassword(String email, String newPassword);

    String sendRegisterMeassage(String email, String lastName, String firstName, String patronymic);
}
