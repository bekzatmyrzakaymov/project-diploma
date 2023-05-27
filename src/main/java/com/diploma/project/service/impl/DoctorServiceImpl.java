package com.diploma.project.service.impl;

import com.diploma.project.model.DoctorRecord;
import com.diploma.project.model.dto.DoctorRecordDto;
import com.diploma.project.model.oauth.User;
import com.diploma.project.repository.DoctorRecordRepository;
import com.diploma.project.repository.oauth.UserRepository;
import com.diploma.project.util.CustomBeanUtils;
import com.diploma.project.util.JpaSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl {
    @Autowired
    private DoctorRecordRepository doctorRecordRepository;
    @Autowired
    private UserRepository userRepository;

    public List<DoctorRecordDto> getDoctorRecordByPatientId(Long id){
        return doctorRecordRepository.findAllByPatientId(id)
                .stream()
                .map(this::convertDoctorRecordDto)
                .collect(Collectors.toList());
    }

    public List<DoctorRecordDto> getDoctorRecordByDoctorId(Long id){
        return doctorRecordRepository.findAllByDoctorId(id)
                .stream()
                .map(this::convertDoctorRecordDto)
                .collect(Collectors.toList());
    }

    private DoctorRecordDto convertDoctorRecordDto(DoctorRecord doctorRecord){
        DoctorRecordDto dto =new DoctorRecordDto();
        CustomBeanUtils.copyPropertiesIgnoreNullPropertyNames(doctorRecord, dto, "patient","doctor");
        if(doctorRecord.getPatient()!=null){
            if(doctorRecord.getPatient().getFullName()!=null){
                dto.setPatient(doctorRecord.getPatient().getFullName());
            }
            if(doctorRecord.getPatient().getId()!=null){
                dto.setPatientId(doctorRecord.getPatient().getId());
            }
        }

        if(doctorRecord.getDoctor()!=null){
            dto.setDoctorId(doctorRecord.getDoctor().getId());
        }
        return dto;
    }

    public List<User> getPatientList(String name){
        JpaSpecificationBuilder<User> builder = new JpaSpecificationBuilder<>();
        builder.addSpecification((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%"+name.trim().toLowerCase()+"%"));
        return userRepository.findAll(builder.build());
    }

    @Transactional
    public DoctorRecordDto createRecord(DoctorRecordDto dto){
        DoctorRecord doctorRecord = new DoctorRecord();
        CustomBeanUtils.copyPropertiesIgnoreNullPropertyNames(dto, doctorRecord, "patient","doctor");
        if(dto.getPatient()!=null){
            try {
                Optional<User> patient = userRepository.findById(dto.getPatientId());
                if(patient.isPresent()){
                    doctorRecord.setPatient(patient.get());
                }
            }catch (Exception e){

            }
        }
        if(dto.getDoctorId()!=null){
            try {
                Optional<User> doctor = userRepository.findById(dto.getDoctorId());
                if(doctor.isPresent()){
                    doctorRecord.setDoctor(doctor.get());
                }
            }catch (Exception e){

            }
        }
        doctorRecordRepository.save(doctorRecord);
        return dto;
    }
}
