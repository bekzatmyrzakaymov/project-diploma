package com.diploma.project.service.impl;

import com.diploma.project.exception.CustomException;
import com.diploma.project.exception.constants.ExceptionConstants;
import com.diploma.project.exception.util.ThrowExceptionUtil;
import com.diploma.project.model.homePage.DoctorList;
import com.diploma.project.model.oauth.EUserStatus;
import com.diploma.project.model.oauth.Role;
import com.diploma.project.model.oauth.User;
import com.diploma.project.model.oauth.dto.UserDto;
import com.diploma.project.repository.homePage.DoctorListRepository;
import com.diploma.project.repository.oauth.RoleRepository;
import com.diploma.project.repository.oauth.UserRepository;
import com.diploma.project.util.CustomBeanUtils;
import com.diploma.project.util.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {

    private final String[] IGNORE_FIELDS = {"roles", "accessRegion", "structuralSubdivision",
            "position", "userSignedDocument;"};

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DoctorListRepository doctorListRepository;

    public UserDto getById(Long id){
        return convertToDto(userRepository.getById(id));
    }


    public List<UserDto> getAllNotEnabledUsers() {
        List<UserDto> users = new ArrayList<>();
        userRepository.findAllByStatus(EUserStatus.NOT_ENABLED).forEach(user -> {
            users.add(convertToDto(user));
        });
        return users;
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> users = new ArrayList<>();
        userRepository.findAllByStatus(EUserStatus.ACTIVE).forEach(user -> {
            users.add(convertToDto(user));
        });
        userRepository.findAllByStatus(EUserStatus.DECLINED).forEach(user -> {
            users.add(convertToDto(user));
        });
        userRepository.findAllByStatus(EUserStatus.DISABLED).forEach(user -> {
            users.add(convertToDto(user));
        });
        return users;
    }


    @Transactional
    public String changePassword(String oldPassword, String newPassword, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(userId.toString(), User.class));
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "Пароль успешно изменен";
        } else {
            ThrowExceptionUtil.throwCustomExceptionByCodeCPE019();
        }
        return "Не удалось сменить пароль";
    }

    @Transactional
    public String changePasswordByModerator(String confirmPassword, String newPassword, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(userId.toString(), User.class));
        if (newPassword.equals(confirmPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "Пароль успешно изменен";
        } else {
            ThrowExceptionUtil.throwCustomExceptionByCodeCPE019();
        }
        return "Не удалось сменить пароль";
    }


    @Modifying
    @Transactional
    public Boolean declineUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.get().setStatus(EUserStatus.DECLINED);
        emailSenderService.send(user.get().getEmail(), "Ваша заявка на регистрацию отклонена.");
        userRepository.save(user.get());
        return user.get().getStatus().equals(EUserStatus.DECLINED);
    }

    @Modifying
    @Transactional
    public Boolean disableUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.get().setStatus(EUserStatus.DISABLED);
        userRepository.save(user.get());
        return user.get().getStatus().equals(EUserStatus.DISABLED);
    }

    @Transactional
    public UserDto changeRole(Long newRoleId, Long userId) {
        final Optional<User> user = userRepository.findById(userId);
        final Role role = roleRepository.findById(newRoleId)
                .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(newRoleId.toString(), Role.class));

        user.get().setRole(role);
        return convertToDto(user.get());
    }

    @Transactional
    public UserDto changeInfo(UserDto userDto){
        final User user = userRepository.findById(userDto.getId())
                .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(userDto.getId().toString(), User.class));
        if(!user.getEmail().equals(userDto.getEmail()) && userDto.getEmail()!=null){
            if (userRepository.existsByEmailAndStatus(userDto.getEmail(), EUserStatus.ACTIVE)) {
                throw new CustomException(ExceptionConstants.LV004, "Пользователь с таким EMAIL уже зарегистрирован");
            }
            user.setEmail(userDto.getEmail());
        }
        if(!user.getRole().getId().equals(userDto.getRole()) && userDto.getRole()!=null){
            final Role role = roleRepository.findById(userDto.getRole())
                    .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(userDto.getRole().toString(), Role.class));

            user.setRole(role);
        }
        if(userDto.getPassword()!=null){
            if(!userDto.getPassword().equals(user.getPassword())){
                if (userDto.getPassword().equals(userDto.getConfirmPassword())) {
                    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                } else {
                    ThrowExceptionUtil.throwCustomExceptionByCodeCPE019();
                }
            }
        }
        userRepository.save(user);
        return convertToDto(user);
    }
    @Transactional
    public UserDto changeEmail(String email, Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(userId.toString(), User.class));

        if (userRepository.existsByEmailAndStatus(email, EUserStatus.ACTIVE)) {
            throw new CustomException(ExceptionConstants.LV004, "Пользователь с таким EMAIL уже зарегистрирован");
        }
        user.setEmail(email);
        return convertToDto(user);
    }


    private UserDto convertToDto(User entity) {
        UserDto dto = new UserDto();
        CustomBeanUtils.copyPropertiesIgnoreNullPropertyNames(entity, dto, IGNORE_FIELDS);
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus().name());
        if (entity.getRole() != null) {
            dto.setRole(entity.getRole().getId());
        }

        DoctorList doctorList = doctorListRepository.findDistinctFirstByUserId(dto.getId());
        if(doctorList!=null){
            if(doctorList.getAddress()!=null){
                dto.setAddress(doctorList.getAddress());
            }
            if(doctorList.getCity()!=null){
                dto.setCity(doctorList.getCity());
            }
            if(doctorList.getClinicList()!=null){
                dto.setClinicList(doctorList.getClinicList());
            }
        }

        return dto;
    }

    private User convertToEntity(User entity, UserDto dto) {
        CustomBeanUtils.copyPropertiesIgnoreNullPropertyNames(dto, entity, IGNORE_FIELDS);
        if (dto.getId() != null) {
            entity = userRepository.getById(dto.getId());
        }
        if (dto.getRole() != null) {
            entity.setRole(roleRepository.findById(dto.getRole())
                    .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(dto.getRole().toString(), Role.class)));
        }
        return entity;
    }

}
