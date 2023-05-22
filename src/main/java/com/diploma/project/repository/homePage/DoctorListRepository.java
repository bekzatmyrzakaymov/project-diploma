package com.diploma.project.repository.homePage;

import com.diploma.project.model.homePage.DoctorList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorListRepository extends JpaRepository<DoctorList,Long>, JpaSpecificationExecutor<DoctorList> {

    DoctorList findDistinctFirstByUserId(Long id);
}
