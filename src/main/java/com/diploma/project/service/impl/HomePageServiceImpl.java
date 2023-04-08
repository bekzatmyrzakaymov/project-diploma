package com.diploma.project.service.impl;

import com.diploma.project.model.HomePage;
import com.diploma.project.repository.HomePageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomePageServiceImpl {

    @Autowired
    public HomePageRepository homePageRepository;

    public List<HomePage> getByTypeID(Integer typeId){
        return homePageRepository.findAllByTypeId(typeId);
    }
}
