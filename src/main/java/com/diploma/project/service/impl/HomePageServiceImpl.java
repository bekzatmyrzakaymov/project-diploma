package com.diploma.project.service.impl;

import com.diploma.project.model.HomePage;
import com.diploma.project.model.homePage.ClinicList;
import com.diploma.project.model.homePage.DoctorList;
import com.diploma.project.model.homePage.MedicamentList;
import com.diploma.project.model.homePage.PharmaciesList;
import com.diploma.project.model.search.HomePageSearch;
import com.diploma.project.model.search.PageSearch;
import com.diploma.project.repository.HomePageRepository;
import com.diploma.project.repository.homePage.ClinicListRepository;
import com.diploma.project.repository.homePage.DoctorListRepository;
import com.diploma.project.repository.homePage.MedicamentListRepository;
import com.diploma.project.repository.homePage.PharmaciesListRepository;
import com.diploma.project.util.JpaSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomePageServiceImpl {

    @Autowired
    private HomePageRepository homePageRepository;
    @Autowired
    private MedicamentListRepository medicamentListRepository;
    @Autowired
    private ClinicListRepository clinicListRepository;
    @Autowired
    private PharmaciesListRepository pharmaciesListRepository;
    @Autowired
    private DoctorListRepository doctorListRepository;

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

    public List<MedicamentList> getMedicamentList(PageSearch homePageSearch){
        JpaSpecificationBuilder<MedicamentList> builder = new JpaSpecificationBuilder<>();

        if(homePageSearch.getText()!=null){
            builder.addSpecification((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                            "%"+homePageSearch.getText().trim().toLowerCase() + "%"));
        }
        return medicamentListRepository.findAll(builder.build());
    }

    public List<ClinicList> getClinicList(PageSearch homePageSearch){
        JpaSpecificationBuilder<ClinicList> builder = new JpaSpecificationBuilder<>();

        if(homePageSearch.getText()!=null){
            builder.addSpecification((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                            "%"+homePageSearch.getText().trim().toLowerCase() + "%"));
        }
        return clinicListRepository.findAll(builder.build());
    }

    public List<PharmaciesList> getPharmaciesList(PageSearch homePageSearch){
        JpaSpecificationBuilder<PharmaciesList> builder = new JpaSpecificationBuilder<>();

        if(homePageSearch.getText()!=null){
            builder.addSpecification((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                            "%"+homePageSearch.getText().trim().toLowerCase() + "%"));
        }
        return pharmaciesListRepository.findAll(builder.build());
    }

    public List<DoctorList> getDoctorList(PageSearch homePageSearch){
        JpaSpecificationBuilder<DoctorList> builder = new JpaSpecificationBuilder<>();

        if(homePageSearch.getText()!=null){
            builder.addSpecification((root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")),
                            "%"+homePageSearch.getText().trim().toLowerCase() + "%"));
        }
        return doctorListRepository.findAll(builder.build());
    }
}
