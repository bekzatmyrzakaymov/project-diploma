package com.diploma.project.controller.diploma;

import com.diploma.project.model.DoctorRecord;
import com.diploma.project.model.dto.DoctorRecordDto;
import com.diploma.project.model.oauth.User;
import com.diploma.project.service.impl.DoctorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor-page")
@Slf4j
public class DoctorPageController {
    @Autowired
    private DoctorServiceImpl doctorService;

    @PostMapping("/create-record")
    public DoctorRecordDto createRecord(@RequestBody DoctorRecordDto dto) {
        log.info("Doctor Page - Create new Patient record");
        return doctorService.createRecord(dto);
    }

    @GetMapping("/patient_list")
    public List<User> getUser(@RequestParam("name") String name) {
        return doctorService.getPatientList(name);
    }

    @GetMapping("/patient-record/{id}")
    public List<DoctorRecordDto> getPatientRecord(@RequestParam("id") Long id) {
        return doctorService.getDoctorRecordByPatientId(id);
    }

    @GetMapping("/doctor-record/{id}")
    public List<DoctorRecordDto> getDoctorRecord(@RequestParam("id") Long id) {
        return doctorService.getDoctorRecordByDoctorId(id);
    }
}
