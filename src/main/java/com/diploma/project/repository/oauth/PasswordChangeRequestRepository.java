package com.diploma.project.repository.oauth;

import com.diploma.project.model.oauth.PasswordChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordChangeRequestRepository extends JpaRepository<PasswordChangeRequest, Long> {


    @Modifying
    @Transactional
    void deleteByExpirationDateTimeIsBefore(LocalDateTime date);

    Optional<PasswordChangeRequest> findByToken(String token);
}
