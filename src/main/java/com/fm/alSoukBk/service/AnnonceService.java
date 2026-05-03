package com.fm.alSoukBk.service;


import com.fm.alSoukBk.dto.AnnonceRequestDTO;
import com.fm.alSoukBk.dto.AnnonceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AnnonceService {

    AnnonceResponseDTO create(AnnonceRequestDTO request);

    List<AnnonceResponseDTO> findAll();

    AnnonceResponseDTO findById(Long id);

    AnnonceResponseDTO updateAnnonce(Long id, AnnonceRequestDTO annonceRequestDTO);

    AnnonceResponseDTO deleteAnnonce(Long id);

    AnnonceResponseDTO validerAnnonce(Long id);

    AnnonceResponseDTO refuserAnnonce(Long id);

    Page<AnnonceResponseDTO> search(String keyword, String category, String regionCode, Double minPrice, Double maxPrice, Pageable pageable);

    Page<AnnonceResponseDTO> findAllByRegionCode(String regionCode, Pageable pageable);
}
