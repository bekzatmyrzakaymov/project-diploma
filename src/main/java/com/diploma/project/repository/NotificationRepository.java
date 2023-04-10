package com.diploma.project.repository;

import com.diploma.project.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>, JpaSpecificationExecutor<Notification> {

    List<Notification> findAllByUserId(Long id);
}
