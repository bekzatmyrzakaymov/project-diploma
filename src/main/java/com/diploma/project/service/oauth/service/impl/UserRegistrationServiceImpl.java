package com.diploma.project.service.oauth.service.impl;
import com.diploma.project.exception.util.ThrowExceptionUtil;
import com.diploma.project.model.oauth.EUserStatus;
import com.diploma.project.model.oauth.Role;
import com.diploma.project.model.oauth.User;
import com.diploma.project.model.oauth.dto.UserDto;
import com.diploma.project.repository.oauth.RoleRepository;
import com.diploma.project.repository.oauth.UserRepository;
import com.diploma.project.service.oauth.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

//--Test
    @Override
    @Transactional
    public Boolean registerUser(UserDto userDto) throws IOException {
        User user = new User(userDto.getIin(), userDto.getLastName(), userDto.getFirstName(), userDto.getPatronymic(),
                userDto.getEmail(), userDto.getPhoneNumber(), userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()));

        user.setStatus(EUserStatus.NOT_ENABLED);
        final Long roleId=1L;
        user.setRole(roleRepository.findById(roleId)
                .orElseThrow(ThrowExceptionUtil.throwCustomExceptionByCodeNF012(roleId.toString(), Role.class)));
        final User savedUser = userRepository.save(user);
        return savedUser.getId() != null;
    }
}
