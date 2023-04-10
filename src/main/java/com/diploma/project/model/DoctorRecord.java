package com.diploma.project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "doctor_record", schema = "public")
public class DoctorRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient")
    private String patient;

    @Column(name = "title")
    private String title;

    @Column(name = "specification",columnDefinition = "TEXT")
    private String specification;

    @Column(name = "is_avail")
    private Boolean isAvail;
}
