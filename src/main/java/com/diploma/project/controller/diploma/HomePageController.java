package com.diploma.project.controller.diploma;

import com.diploma.project.model.HomePage;
import com.diploma.project.model.homePage.ClinicList;
import com.diploma.project.model.homePage.DoctorList;
import com.diploma.project.model.homePage.MedicamentList;
import com.diploma.project.model.homePage.PharmaciesList;
import com.diploma.project.model.search.HomePageSearch;
import com.diploma.project.model.search.PageSearch;
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
        log.info("Home Page - get form by type");
        return homePageService.getHomePage(homePageSearch,id);
    }

    @PostMapping("/medicaments")
    public List<MedicamentList> getMedicaments(@RequestBody PageSearch pageSearch) {
        log.info("Get medicaments Page");
        return homePageService.getMedicamentList(pageSearch);
    }

    @PostMapping("/clinics")
    public List<ClinicList> getClinicList(@RequestBody PageSearch pageSearch) {
        log.info("Get clinic Page");
        return homePageService.getClinicList(pageSearch);
    }

    @PostMapping("/pharmacies")
    public List<PharmaciesList> getPharmaciesList(@RequestBody PageSearch pageSearch) {
        log.info("Get pharmacies Page");
        return homePageService.getPharmaciesList(pageSearch);
    }

    @PostMapping("/doctors")
    public List<DoctorList> getDoctorList(@RequestBody PageSearch pageSearch) {
        log.info("Get pharmacies Page");
        return homePageService.getDoctorList(pageSearch);
    }
}
