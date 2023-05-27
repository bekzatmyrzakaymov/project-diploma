package com.diploma.project.model;

import com.diploma.project.model.oauth.User;
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

//    @Column(name = "patient")
//    private String patient;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @Column(name = "title",columnDefinition = "TEXT")
    private String title;

    @Column(name = "specification",columnDefinition = "TEXT")
    private String specification;

    @Column(name = "file_path", columnDefinition = "TEXT")
    private String filePath;

    @Column(name = "is_avail")
    private Boolean isAvail;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;
}
