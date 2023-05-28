package com.diploma.project.service.impl;

import com.diploma.project.model.DoctorRecord;
import com.diploma.project.model.Review;
import com.diploma.project.model.dto.DoctorRecordDto;
import com.diploma.project.model.dto.ReviewDto;
import com.diploma.project.model.oauth.User;
import com.diploma.project.repository.DoctorRecordRepository;
import com.diploma.project.repository.ReviewRepository;
import com.diploma.project.repository.oauth.UserRepository;
import com.diploma.project.util.CustomBeanUtils;
import com.diploma.project.util.JpaSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl {
    @Autowired
    private DoctorRecordRepository doctorRecordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewDto> getReviewList(Long id){
        return reviewRepository.findAllByDoctorId(id)
                .stream()
                .map(this::convertReview)
                .collect(Collectors.toList());
    }

    private ReviewDto convertReview(Review review){
        ReviewDto dto = new ReviewDto();
        CustomBeanUtils.copyPropertiesIgnoreNullPropertyNames(review, dto, "patient","doctor");
        if(review.getPatient()!=null){
            dto.setPatientId(review.getPatient().getId());
        }
        if(review.getDoctor()!=null){
            dto.setDoctorId(review.getDoctor().getId());
        }
        return dto;
    }

    public String createReview(ReviewDto reviewDto){
        Review review = new Review();
        if(reviewDto.getReviewText()!=null){
            review.setReviewText(reviewDto.getReviewText());
        }
        if(reviewDto.getRating()!=null){
            review.setRating(reviewDto.getRating());
        }
        if(reviewDto.getDoctorId()!=null){
            Optional<User> doctor = userRepository.findById(reviewDto.getDoctorId());
            if(doctor.isPresent()){
                review.setDoctor(doctor.get());
            }
        }

        if(reviewDto.getPatientId()!=null){
            Optional<User> patient = userRepository.findById(reviewDto.getPatientId());
            if(patient.isPresent()){
                review.setPatient(patient.get());
            }
        }
        review.setCreateDate(LocalDateTime.now());
        reviewRepository.save(review);
        return "Успешно";
    }

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
