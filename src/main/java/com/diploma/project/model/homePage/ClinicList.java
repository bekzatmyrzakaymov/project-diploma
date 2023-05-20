package com.diploma.project.model.homePage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "clinic_list", schema = "public")
public class ClinicList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "work_time")
    private String workTime;

    @Column(name = "work_days")
    private String workDays;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "photo")
    private String photo;
}
