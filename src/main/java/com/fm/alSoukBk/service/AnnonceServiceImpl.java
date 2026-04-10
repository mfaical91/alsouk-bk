package com.fm.alSoukBk.service;

import com.fm.alSoukBk.model.Annonce;
import com.fm.alSoukBk.repository.AnnonceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AnnonceServiceImpl implements AnnonceService {

    private final AnnonceRepository annonceRepository;

    @Override
    public Annonce createAnnonce(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    @Override
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    @Override
    public Annonce getAnnonceById(Long id) {
        return annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
    }

    @Override
    public Annonce updateAnnonce(Long id, Annonce updatedAnnonce) {
        Annonce existing = getAnnonceById(id);
        existing.setTitle(updatedAnnonce.getTitle());
        existing.setDescription(updatedAnnonce.getDescription());
        existing.setPrice(updatedAnnonce.getPrice());
        existing.setLocation(updatedAnnonce.getLocation());
        existing.setCategory(updatedAnnonce.getCategory());
        return annonceRepository.save(existing);
    }

    @Override
    public void deleteAnnonce(Long id) {
        annonceRepository.deleteById(id);
    }


    public Annonce validerAnnonce(Long id) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
        annonce.setActive(true);
        return annonceRepository.save(annonce);
    }

    public Annonce refuserAnnonce(Long id) {
        Annonce annonce = annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
        annonce.setActive(false);
        return annonceRepository.save(annonce);
    }

    @Override
    public Page<Annonce> getAnnoncesByRegionCode(String regionCode, Pageable pageable) {
        return annonceRepository.findByRegionCode(regionCode, pageable);
    }

}
