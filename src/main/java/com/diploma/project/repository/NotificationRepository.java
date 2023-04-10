package com.diploma.project.repository;

import com.diploma.project.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>, JpaSpecificationExecutor<Notification> {

    List<Notification> findAllByUserId(Long id);

    @Transactional
    @Modifying
    @Query(value = "update notification " +
            "set is_done =true " +
            "where user_id=:id ",nativeQuery = true)
    void notificationDone(Long id);
}
