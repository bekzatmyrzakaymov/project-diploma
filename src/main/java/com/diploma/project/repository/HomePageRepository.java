package com.diploma.project.repository;

import com.diploma.project.model.HomePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomePageRepository extends JpaRepository<HomePage,Long>, JpaSpecificationExecutor<HomePage> {

    List<HomePage> findAllByTypeId(Integer typeId);
}
