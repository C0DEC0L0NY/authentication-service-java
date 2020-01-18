package com.gregburgoon.authenticationservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name="registered_user")
@NoArgsConstructor
@Table(name="registered_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String email;

    @ManyToMany
    @Getter @Setter
    private List<Role> roles;

    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String password;

    @Transient
    @Getter @Setter
    private String passwordConfirmation;

}
