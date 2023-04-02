package com.diploma.project.model.oauth.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


public class JwtUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String iin;
    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final String phoneNumber;
    private final String username;
    @JsonIgnore
    private final String password;
    private final String email;
    private final List<? extends GrantedAuthority> authorities;


    public JwtUser(Long id, String iin, String lastName,
                   String firstName, String patronymic,
                   String phoneNumber, String password,
                   String email, String username, List<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.iin = iin;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getIin() {
        return iin;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JwtUser other = (JwtUser) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
