package com.diploma.project.model.homePage;

import com.diploma.project.model.oauth.Role;
import com.diploma.project.model.oauth.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "doctor_list", schema = "public")
public class DoctorList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "photo")
    private String photo;

    @OneToOne
    @JoinColumn(name = "clinic_list_id")
    private ClinicList clinicList;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
