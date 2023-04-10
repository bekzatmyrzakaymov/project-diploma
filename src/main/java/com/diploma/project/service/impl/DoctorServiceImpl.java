package com.diploma.project.service.impl;

import com.diploma.project.model.DoctorRecord;
import com.diploma.project.model.dto.DoctorRecordDto;
import com.diploma.project.repository.DoctorRecordRepository;
import com.diploma.project.util.CustomBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctorServiceImpl {
    @Autowired
    private DoctorRecordRepository doctorRecordRepository;

    @Transactional
    public DoctorRecordDto createRecord(DoctorRecordDto dto){
        DoctorRecord doctorRecord = new DoctorRecord();
        CustomBeanUtils.copyPropertiesIgnoreNullPropertyNames(dto, doctorRecord);
        doctorRecordRepository.save(doctorRecord);
        return dto;
    }
}
