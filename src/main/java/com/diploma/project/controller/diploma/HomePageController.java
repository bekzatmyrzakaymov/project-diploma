package com.diploma.project.controller.diploma;

import com.diploma.project.model.HomePage;
import com.diploma.project.service.impl.HomePageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home-page")
@Slf4j
public class HomePageController {
    @Autowired
    private HomePageServiceImpl homePageService;

    @GetMapping("/{id}")
    public List<HomePage> get(@PathVariable("id") Integer id) {
        return homePageService.getByTypeID(id);
    }
}
