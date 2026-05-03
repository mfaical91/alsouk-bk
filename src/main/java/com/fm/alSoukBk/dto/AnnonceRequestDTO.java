// AnnonceRequestDTO.java
package com.fm.alSoukBk.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Data
public class AnnonceRequestDTO {
    private String title;
    private String description;
    private Double price;
    private String location;
    private String regionCode;
    private String category;
    private String image;


}


