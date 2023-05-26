package com.diploma.project.model.oauth;

;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users", schema = "oauth")
@SequenceGenerator(name = "default_abstract_entity_gen", sequenceName = "SEQ_USERS", allocationSize = 1, schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "photo")
    private String photo;
    private String password;
    private Boolean enabled = true;

    public User() {}

    public User(String email, String phoneNumber,
                String password) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;

    }

    @ManyToOne
    private Role role;

    @Enumerated(EnumType.STRING)
    private EUserStatus status;
}
