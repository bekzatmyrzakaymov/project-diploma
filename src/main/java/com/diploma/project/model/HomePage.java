package com.diploma.project.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "home_page", schema = "public")
@SequenceGenerator(name = "default_abstract_entity_gen", sequenceName = "SEQ_HOME_PAGE", allocationSize = 1, schema = "public")
public class HomePage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fio")
    private String fio;

    @Column(name = "position")
    private String position;

    @Column(name = "city")
    private String city;

    @Column(name = "clinic_name")
    private String clinicName;

    @Column(name = "type_id")
    private Integer typeId;
}
