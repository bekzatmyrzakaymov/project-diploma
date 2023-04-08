package com.diploma.project.model.oauth.jwt;

import com.diploma.project.exception.CustomException;
import com.diploma.project.exception.constants.ExceptionConstants;
import com.diploma.project.model.oauth.EUserStatus;
import com.diploma.project.model.oauth.User;
import com.diploma.project.repository.oauth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        Optional<User> userOptional = userRepository.findByEmailAndStatus(username, EUserStatus.ACTIVE);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else if (userRepository.findAllByEmailAndStatus(username, EUserStatus.NOT_ENABLED).size() >= 1) {
            throw new CustomException(ExceptionConstants.LV005);
        } else if (userRepository.findAllByEmailAndStatus(username, EUserStatus.DECLINED).size() >= 1) {
            throw new CustomException(ExceptionConstants.IU006);
        } else if (userRepository.findAllByEmailAndStatus(username, EUserStatus.DISABLED).size() >= 1) {
            throw new CustomException(ExceptionConstants.IU004);
        } else {
            throw new CustomException(ExceptionConstants.IUP05);
        }
        JwtUser userDetails = JwtUserFactory.create(user);
        return userDetails;
    }
}
