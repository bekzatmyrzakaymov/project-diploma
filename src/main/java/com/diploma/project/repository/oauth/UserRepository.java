package com.diploma.project.repository.oauth;

import com.diploma.project.model.oauth.EUserStatus;
import com.diploma.project.model.oauth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    List<User> findAllByStatus(EUserStatus status);

    List<User> findAllByEmailAndStatus(String iin, EUserStatus status);

    Boolean existsByEmailAndStatus(String email, EUserStatus status);

    Optional<User> findByEmailAndStatus(String email, EUserStatus status);

}
