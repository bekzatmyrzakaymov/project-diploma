package com.diploma.project.controller.diploma;

import com.diploma.project.model.HomePage;
import com.diploma.project.model.search.HomePageSearch;
import com.diploma.project.service.impl.HomePageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home-page")
@Slf4j
public class HomePageController {
    //--Главная страница
    @Autowired
    private HomePageServiceImpl homePageService;

    @PostMapping("/{id}")
    public List<HomePage> get(@PathVariable("id") Integer id,
                              @RequestBody HomePageSearch homePageSearch) {
        return homePageService.getHomePage(homePageSearch,id);
    }
}
