package com.gregburgoon.authenticationservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name="role")
@NoArgsConstructor
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Getter @Setter
    private Set<User> users;
}
