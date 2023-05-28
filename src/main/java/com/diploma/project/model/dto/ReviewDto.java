package com.diploma.project.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long id;
    private Long patientId;
    private String reviewText;
    private Double rating;
    private LocalDateTime createDate;
    private Long doctorId;
}
