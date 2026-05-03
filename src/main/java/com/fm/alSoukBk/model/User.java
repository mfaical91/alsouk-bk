package com.fm.alSoukBk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_username_email",
                        columnNames = {"username", "email"}
                )
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(columnDefinition = "VARCHAR(255) CHECK (email ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'")
    private String email;


    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")  // Important : roles_id
    )
    private Set<Role> roles = new HashSet<>();

}
