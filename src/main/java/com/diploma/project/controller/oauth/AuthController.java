package com.diploma.project.controller.oauth;

import com.diploma.project.exception.CustomException;
import com.diploma.project.exception.constants.ExceptionConstants;
import com.diploma.project.model.oauth.EUserStatus;
import com.diploma.project.model.oauth.LoginRequest;
import com.diploma.project.model.oauth.User;
import com.diploma.project.model.oauth.dto.UserDto;
import com.diploma.project.model.oauth.jwt.JwtTokenProvider;
import com.diploma.project.repository.oauth.UserRepository;
import com.diploma.project.service.oauth.service.impl.UserRegistrationServiceImpl;
import com.diploma.project.util.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class AuthController {

    //--Авторизация/Регистрация
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRegistrationServiceImpl userRegistrationService;



    @PostMapping("/signin") //--Авторизация
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {

            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            Optional<User> user = userRepository.findByEmail(username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.get().getEmail(), password));

            String token = jwtTokenProvider.createToken(user.get().getEmail(), user.get().getRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", user.get().getEmail());
            response.put("token", token);


            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            if (userRepository.findAllByEmailAndStatus(loginRequest.getUsername(), EUserStatus.NOT_ENABLED).size() >= 1) {
                throw new CustomException(ExceptionConstants.IU004, "Ваш аккаунт не активирован");
            }
            if (userRepository.findAllByEmailAndStatus(loginRequest.getUsername(), EUserStatus.DECLINED).size() >= 1) {
                throw new CustomException(ExceptionConstants.IU004, "Ваш запрос на регистрацию отклонен");
            }
            if (userRepository.findAllByEmailAndStatus(loginRequest.getUsername(), EUserStatus.DISABLED).size() >= 1) {
                throw new CustomException(ExceptionConstants.IU004, "Ваш аккаунт отключен");
            }
            throw new CustomException(ExceptionConstants.IUP05);
        }
    }

    @PostMapping("/signup")
    @Transactional //--Регистрация
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto,
                                          HttpServletRequest request) throws IOException {
        if (userRepository.existsByEmailAndStatus(userDto.getEmail(), EUserStatus.NOT_ENABLED)) {
            throw new CustomException(ExceptionConstants.LV005, String.format(ExceptionConstants.LV005.getTemplateMessage(), userDto.getEmail()));
        }
        if (userRepository.existsByEmailAndStatus(userDto.getEmail(), EUserStatus.ACTIVE)) {
            throw new CustomException(ExceptionConstants.LV004, String.format(ExceptionConstants.LV004.getTemplateMessage(), userDto.getEmail()));
        }
        if (userRegistrationService.registerUser(userDto)) {
            return ResponseEntity.ok(new MessageResponse("Пользователь успешно зарегистрирован"));
        }
        throw new CustomException(ExceptionConstants.IA014);
    }

}
