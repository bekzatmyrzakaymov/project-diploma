package com.diploma.project.repository.homePage;

import com.diploma.project.model.homePage.ClinicList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicListRepository extends JpaRepository<ClinicList,Long>, JpaSpecificationExecutor<ClinicList> {
}
