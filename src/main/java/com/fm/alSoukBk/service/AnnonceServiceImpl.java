package com.fm.alSoukBk.service;


import com.fm.alSoukBk.dto.AnnonceRequestDTO;
import com.fm.alSoukBk.dto.AnnonceResponseDTO;
import com.fm.alSoukBk.exception.ResourceNotFoundException;
import com.fm.alSoukBk.mapper.AnnonceMapper;
import com.fm.alSoukBk.mapper.AnnonceMapperImpl;
import com.fm.alSoukBk.model.Annonce;
import com.fm.alSoukBk.model.User;
import com.fm.alSoukBk.repository.AnnonceRepository;
import com.fm.alSoukBk.specification.AnnonceSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnonceServiceImpl implements AnnonceService {

    private final AnnonceRepository annonceRepository;
    private static final Logger log = LoggerFactory.getLogger(AnnonceServiceImpl.class);


    @Override
    public AnnonceResponseDTO create(AnnonceRequestDTO request) {
        Annonce annonceNew = AnnonceMapperImpl.INSTANCE.toEntity(request);
        Annonce annonceSaved =annonceRepository.save(annonceNew);
        return AnnonceMapperImpl.INSTANCE.toDTO(annonceSaved);
    }


    private Annonce enrichAnnonce(Annonce annonce) {
        annonce.setCreatedAt(LocalDateTime.now());
        annonce.setActive(true);
        return annonce;
    }


    @Override
    public List<AnnonceResponseDTO> findAll() {
        return annonceRepository.findAll().stream().map(AnnonceMapperImpl.INSTANCE::toDTO).toList();
    }


    @Override
    public AnnonceResponseDTO findById(Long id) {
        return annonceRepository.findById(id).map(AnnonceMapperImpl.INSTANCE::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce non trouvée avec l'id : " + id));
    }


    @Override
    public AnnonceResponseDTO updateAnnonce(Long id, AnnonceRequestDTO request,User user) {

        Annonce annonce = annonceRepository.   //todo a revoir
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Annonce non trouvée avec l'id : " + id));

        if (!annonce.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Accès refusé");
        }

        annonce.setPrice(request.getPrice());
        Annonce annonceSaved = annonceRepository.save(annonce);

        return AnnonceMapperImpl.INSTANCE.toDTO(annonceSaved);
    }


    @Override
    public AnnonceResponseDTO deleteAnnonce(Long id) {
        Annonce annonce = annonceRepository.
                findById(id).orElseThrow(() -> new ResourceNotFoundException("Annonce non trouvée"));
        annonceRepository.deleteById(id);    //todo a revoir
        return AnnonceMapperImpl.INSTANCE.toDTO(annonce);
    }


    public AnnonceResponseDTO validerAnnonce(Long id) {
        Annonce annonce = annonceRepository.
                findById(id).orElseThrow(() -> new ResourceNotFoundException("Annonce non trouvée"));
        annonce.setActive(true);
        Annonce annonceSaved= annonceRepository.save(annonce);  //todo a revoir
        return AnnonceMapperImpl.INSTANCE.toDTO(annonceSaved);
    }

    public AnnonceResponseDTO refuserAnnonce(Long id) {
        Annonce annonce = annonceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Annonce non trouvée"));
        annonce.setActive(false);
        Annonce annonceSaved= annonceRepository.save(annonce);  //todo a revoir
        return AnnonceMapperImpl.INSTANCE.toDTO(annonceSaved);
    }

    @Override
    public Page<AnnonceResponseDTO> search(String keyword, String category, String regionCode, Double minPrice, Double maxPrice, Pageable pageable) {
        Specification<Annonce> spec = AnnonceSpecification.search(
                keyword, category, regionCode, minPrice, maxPrice
        );

        return annonceRepository.findAll(spec, pageable)
                .map(AnnonceMapperImpl.INSTANCE::toDTO);
    }

    @Override
    public Page<AnnonceResponseDTO> findAllByRegionCode(String regionCode, Pageable pageable) {
        return annonceRepository.findByRegionCode(regionCode,pageable)
                .map(AnnonceMapperImpl.INSTANCE::toDTO);}


    public void deleteAnnonceByOwner(Long id, User user) {

        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Annonce introuvable"));

        if (!annonce.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Accès refusé");
        }

        annonceRepository.delete(annonce);
    }

    @Override
    public List<AnnonceResponseDTO> findByUser(User user) {
        List<Annonce> annonces = annonceRepository.findByUser(user);

        return annonces.stream()
                .map(AnnonceMapperImpl.INSTANCE::toDTO)
                .toList();
    }


}
