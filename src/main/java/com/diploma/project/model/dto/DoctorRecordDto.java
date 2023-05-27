package com.diploma.project.model.dto;

import lombok.Data;


@Data
public class DoctorRecordDto {
    private String patient;
    private String title;
    private String specification;
    private Boolean isAvail;
    private String filePath;
    private Long doctorId;
    private Long patientId;
}
