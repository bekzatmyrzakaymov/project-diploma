package com.diploma.project.repository;

import com.diploma.project.model.DoctorRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRecordRepository extends JpaRepository<DoctorRecord,Long> {

    List<DoctorRecord> findAllByDoctorId(Long id);

    List<DoctorRecord> findAllByPatientId(Long id);
}
