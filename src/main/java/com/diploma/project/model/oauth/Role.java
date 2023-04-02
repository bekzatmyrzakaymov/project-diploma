package com.diploma.project.model.oauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role", schema = "oauth")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Role {

    @Id
    @SequenceGenerator(name = "roles", sequenceName = "SEQ_ROLES", allocationSize = 1, schema = "oauth")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<User> users;

}
