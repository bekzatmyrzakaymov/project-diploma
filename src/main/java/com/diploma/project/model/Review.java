package com.diploma.project.model;

import com.diploma.project.model.oauth.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "review", schema = "public")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @Column(name = "review_text",columnDefinition = "TEXT")
    private String reviewText;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;
}
