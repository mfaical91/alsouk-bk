package com.fm.alSoukBk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "annonces")
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String title;
    private String description;
    private Double price;
    @Column(name = "region_code")
    private String regionCode;
    private String location;
    private String category;
    private String imageUrl;
    @ManyToOne
    private User user;
    private boolean isActive;
    private LocalDateTime createdAt;
}
