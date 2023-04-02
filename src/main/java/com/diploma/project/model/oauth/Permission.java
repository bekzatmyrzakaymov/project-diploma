package com.diploma.project.model.oauth;//package com.samgau.kfm.model.oauth;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Table(name = "permission", schema = "oauth")
//@Getter
//@Setter
//@NoArgsConstructor
//public class Permission {
//
//    @Id
//    @SequenceGenerator(name = "permissions", sequenceName = "SEQ_PERMISSIONS", allocationSize = 1, schema = "oauth")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long id;
//
//    private String name;
//
//    @ManyToMany(mappedBy = "permissions")
//    private List<Role> roles;
//
//
//}
