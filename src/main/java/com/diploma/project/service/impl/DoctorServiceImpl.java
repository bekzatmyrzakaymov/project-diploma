package com.diploma.project.service.impl;

import com.diploma.project.model.DoctorRecord;
import com.diploma.project.model.dto.DoctorRecordDto;
import com.diploma.project.model.oauth.User;
import com.diploma.project.repository.DoctorRecordRepository;
import com.diploma.project.repository.oauth.UserRepository;
import com.diploma.project.util.CustomBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DoctorServiceImpl {
    @Autowired
    private DoctorRecordRepository doctorRecordRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public DoctorRecordDto createRecord(DoctorRecordDto dto){
        DoctorRecord doctorRecord = new DoctorRecord();
        CustomBeanUtils.copyPropertiesIgnoreNullPropertyNames(dto, doctorRecord, "user");
        if(dto.getDoctorId()!=null){
            try {
                Optional<User> doctor = userRepository.findById(dto.getDoctorId());
                if(doctor.isPresent()){
                    doctorRecord.setUser(doctor.get());
                }
            }catch (Exception e){

            }
        }
        doctorRecordRepository.save(doctorRecord);
        return dto;
    }
}
