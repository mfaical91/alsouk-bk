package com.fm.alSoukBk.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "annonces")
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private boolean isActive = true;
    private Date createdAt = new Date();
}
