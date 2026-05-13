package com.fm.alSoukBk.controller;

import com.fm.alSoukBk.dto.AnnonceResponseDTO;
import com.fm.alSoukBk.service.AnnonceService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/annonces")
public class AdminController {

    private final AnnonceService annonceService;

    public AdminController(AnnonceService annonceService) {
        this.annonceService = annonceService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        annonceService.deleteAnnonce(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<AnnonceResponseDTO> valider(@PathVariable Long id) {
        return ResponseEntity.ok(annonceService.validerAnnonce(id));
    }

    @PutMapping("/{id}/refuser")
    public ResponseEntity<AnnonceResponseDTO> refuser(@PathVariable Long id) {
        return ResponseEntity.ok(annonceService.refuserAnnonce(id));
    }


}


