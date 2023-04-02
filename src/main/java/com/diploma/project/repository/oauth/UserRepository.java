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

    Optional<User> findByIin(String iin);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByIinAndStatus(String iin, EUserStatus status);

    List<User> findAllByIinAndStatus(String iin, EUserStatus status);

    Boolean existsByEmailAndStatus(String email, EUserStatus status);

    List<User> findAllByStatus(EUserStatus status);

    Optional<User> findByIinAndStatus(String iin, EUserStatus status);

    Optional<User> findByEmailAndStatus(String email, EUserStatus status);

}
