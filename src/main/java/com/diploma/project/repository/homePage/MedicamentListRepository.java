package com.diploma.project.repository.homePage;

import com.diploma.project.model.homePage.MedicamentList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentListRepository extends JpaRepository<MedicamentList,Long>, JpaSpecificationExecutor<MedicamentList> {
}
