package com.diploma.project.service.impl;

import com.diploma.project.model.HomePage;
import com.diploma.project.model.search.HomePageSearch;
import com.diploma.project.repository.HomePageRepository;
import com.diploma.project.util.JpaSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomePageServiceImpl {

    @Autowired
    public HomePageRepository homePageRepository;

    //--Главная страницы
    public List<HomePage> getHomePage(HomePageSearch homePageSearch, Integer typeId){
        JpaSpecificationBuilder<HomePage> builder = new JpaSpecificationBuilder<>();

        if(homePageSearch.getFio()!=null){ //--Поиск по фио
            builder.addSpecification((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fio")),
                            "%"+homePageSearch.getFio().trim().toLowerCase() + "%"));
        }

        if(homePageSearch.getPosition()!=null){ //-- Поиск по позиции
            builder.addSpecification((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("position")),
                            "%"+homePageSearch.getPosition().trim().toLowerCase() + "%"));
        }

        if(homePageSearch.getCity()!=null){ //-- Поиск по город
            builder.addSpecification((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("city")),
                            "%"+homePageSearch.getCity().trim().toLowerCase() + "%"));
        }

        if(homePageSearch.getClinicName()!=null){ //-- Поиск по название клиники
            builder.addSpecification((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("clinicName")),
                            "%"+homePageSearch.getClinicName().trim().toLowerCase() + "%"));
        }

        //--Тип(доктор, клиника)
        builder.addSpecification((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("typeId"),typeId));
        return homePageRepository.findAll(builder.build());
    }
}
