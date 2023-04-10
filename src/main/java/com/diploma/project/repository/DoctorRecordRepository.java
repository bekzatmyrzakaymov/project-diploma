package com.diploma.project.repository;

import com.diploma.project.model.DoctorRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRecordRepository extends JpaRepository<DoctorRecord,Long> {
}
