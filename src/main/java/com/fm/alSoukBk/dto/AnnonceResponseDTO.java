package com.fm.alSoukBk.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AnnonceResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String location;
    private String regionCode;
    private String category;
    private String imageUrl;
    private String status;
    private LocalDateTime createdAt;
    private UserSummaryDTO user;
}
