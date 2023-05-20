package com.diploma.project.repository.homePage;

import com.diploma.project.model.homePage.PharmaciesList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmaciesListRepository extends JpaRepository<PharmaciesList,Long>, JpaSpecificationExecutor<PharmaciesList> {
}
