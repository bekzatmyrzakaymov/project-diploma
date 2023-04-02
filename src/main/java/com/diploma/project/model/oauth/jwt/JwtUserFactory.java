package com.diploma.project.model.oauth.jwt;

import com.diploma.project.model.oauth.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

public class JwtUserFactory {


    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(),
                user.getIin(),
                user.getLastName(),
                user.getFirstName(),
                user.getPatronymic(),
                user.getPhoneNumber(),
                user.getPassword(),
                user.getEmail(),
                user.getUsername(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName())));
    }

//    private static List<GrantedAuthority> mapPermissionsToGrantedAuthorities(List<Role> userRoles) {
//        return userRoles
//                .stream()
//                .filter(Objects::nonNull)
//                .map(Role::getPermissions)
//                .flatMap(Collection::stream)
//                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
//                .collect(Collectors.toList());
//    }
}
