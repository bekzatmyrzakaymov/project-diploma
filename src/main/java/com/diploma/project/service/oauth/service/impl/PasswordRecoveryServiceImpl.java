//package com.diploma.project.service.oauth.service.impl;
//
//import com.diploma.project.exception.CustomException;
//import com.diploma.project.exception.constants.ExceptionConstants;
//import com.diploma.project.exception.util.ThrowExceptionUtil;
//import com.diploma.project.model.oauth.EUserStatus;
//import com.diploma.project.model.oauth.PasswordChangeRequest;
//import com.diploma.project.model.oauth.User;
//import com.diploma.project.repository.oauth.UserRepository;
//import com.diploma.project.service.oauth.service.PasswordRecoveryService;
//import com.diploma.project.util.EmailSender;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.NoSuchElementException;
//
//@Service
//public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private EmailSender emailSender;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//
//    private final static int EXPIRATION_MS = 3600000;
//    private final static String JWT_SECRET = "MegaLargeSigningSecretKeyForDemoApplicationMegaLargeSigningSecretKeyForDemoApplication";
//
//    @Override
//    public String sendRegisterMeassage(String email, String lastName, String firstName, String patronymic){
//        String text = "Уважаемый, " + lastName + " " + firstName + " " + patronymic +
//                " Вы подали заявку на регистрацию в Информационной Системе Экономических Расследований." + "<br>" +
//                "Ожидайте подтверждения заявки, Вам дополнительно будет отправлено письмо на электронную почту об успешном завершении регистрации.";
//
//        sendEmailRecover(email, text);
//        return "Письмо было отправлено на электронную почту";
//    }
//
//
//    @Override
//    public String forgotPassword(String iin) {
//        User user = userRepository.findByIinAndStatus(iin, EUserStatus.ACTIVE).orElseThrow(() -> new CustomException(ExceptionConstants.NF012, "User was not found by IIN"));
//        String token = generateToken(user.getEmail());
//        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest();
//        passwordChangeRequest.setEmail(user.getEmail());
//        passwordChangeRequest.setCreationDateTime(LocalDateTime.now());
//        passwordChangeRequest.setExpirationDateTime(LocalDateTime.now().plusHours(1));
//        passwordChangeRequest.setToken(token);
//        String link = "Здравствуйте! Кто-то запросил восстановление пароля для аккаунта зарегистрированного по этому E-mail адресу: " + user.getEmail() + " <br>" +
//                " Этот запрос создан " + LocalDateTime.now() + " <br> " +
//                "Если Вы не делали запроса на изменение пароля, проигнорируйте и немедленно удалите это письмо. <br> " +
//                "Продолжайте только в том случае, если Вам действительно требуется восстановление пароля! <br> " +
//                "Если этот запрос был отправлен Вами, пройдите по следующей ссылке: <br> " +
//                "<a href=" + "http://10.202.38.148/user/change-password?token="+token+">" +"http://10.202.38.148/user/change-password?token="+ token +"</a>"+"<br>";
//        changeRequestRepository.save(passwordChangeRequest);
//        sendEmailRecover(user.getEmail(), link);
//        return "Инструкция по смене пароля отправлена на указанный email.";
//    }
//
//    @Override
//    public Boolean resetPassword(String token) {
//        if (changeRequestRepository.findByToken(token).isPresent()) {
//            PasswordChangeRequest passwordChangeRequest = changeRequestRepository.findByToken(token).get();
//            return passwordChangeRequest.getExpirationDateTime().isAfter(LocalDateTime.now());
//        }
//        return false;
//    }
//
//    public Long findUserByToken(String token) {
//        PasswordChangeRequest passwordChangeRequest = changeRequestRepository.findByToken(token).get();
//        User user = userRepository.findByEmailAndStatus(passwordChangeRequest.getEmail(), EUserStatus.ACTIVE)
//                .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(passwordChangeRequest.getEmail(), User.class));
//        return user.getId();
//    }
//
//    @Override
//    public Boolean restorePassword(String token, String newPassword) throws NoSuchElementException {
//        PasswordChangeRequest passwordChangeRequest = changeRequestRepository.findByToken(token).get();
//        User user = userRepository.findByEmailAndStatus(passwordChangeRequest.getEmail(), EUserStatus.ACTIVE)
//                .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(passwordChangeRequest.getEmail(), User.class));
//        user.setPassword(passwordEncoder.encode(newPassword));
//        passwordHistoryRepository.save(new PasswordHistory(LocalDateTime.now(), user));
//        userRepository.save(user);
//        return true;
//    }
//
//    private String generateToken(String email) {
//        Claims claims = Jwts.claims().setSubject(email);
//        Date now = new Date();
//        Date exp = new Date(now.getTime() + EXPIRATION_MS);
//        return Jwts.builder()//
//                .setClaims(claims)//
//                .setIssuedAt(now)//
//                .setExpiration(exp)//
//                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
//                .compact();
//    }
//
//    private void sendEmailRecover(String email, String emailText) {
//        emailSender.send(email, emailText);
//    }
//
//    @Scheduled(cron = "0 0 0 * * *")
//    private void removeExpiredTokensFromDB() {
//        LocalDateTime now = LocalDateTime.now();
//        changeRequestRepository.deleteByExpirationDateTimeIsBefore(now);
//    }
//}
