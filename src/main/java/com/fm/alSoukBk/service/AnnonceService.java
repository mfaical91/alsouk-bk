package com.fm.alSoukBk.service;

import com.fm.alSoukBk.model.Annonce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnnonceService {
    Annonce createAnnonce(Annonce annonce);
    List<Annonce> getAllAnnonces();
    Annonce getAnnonceById(Long id);
    Annonce updateAnnonce(Long id, Annonce annonce);
    void deleteAnnonce(Long id);
    Annonce validerAnnonce(Long id);
    Annonce refuserAnnonce(Long id) ;

    Page<Annonce> getAnnoncesByRegionCode(String regionCode, Pageable pageable);
}
