package com.fm.alSoukBk.controller;

import com.fm.alSoukBk.dto.AnnonceRequestDTO;
import com.fm.alSoukBk.dto.AnnonceResponseDTO;
import com.fm.alSoukBk.dto.PageResponseDTO;
import com.fm.alSoukBk.service.AnnonceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/annonces")
public class AnnonceController {

    private final AnnonceService annonceService;

    public AnnonceController(AnnonceService annonceService) {
        this.annonceService = annonceService;
    }

    @PostMapping
    public ResponseEntity<AnnonceResponseDTO> create(@Valid @RequestBody AnnonceRequestDTO request) {

        AnnonceResponseDTO created = annonceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);


    }

    @GetMapping("")
    public ResponseEntity<List<AnnonceResponseDTO>> findAll() {
        List<AnnonceResponseDTO> annonces = annonceService.findAll();
        return ResponseEntity.ok(annonces);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnonceResponseDTO> findById(@PathVariable Long id) {
        AnnonceResponseDTO result = annonceService.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponseDTO<AnnonceResponseDTO>> searchAnnonces(

            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String regionCode,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,

            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<AnnonceResponseDTO> page = annonceService.search(
                keyword, category, regionCode, minPrice, maxPrice, pageable);

        return ResponseEntity.ok(new PageResponseDTO<>(page));
    }


    @GetMapping("/region/{regionCode}")
    public ResponseEntity<PageResponseDTO<AnnonceResponseDTO>> findAllByRegionCode(
            @PathVariable String regionCode,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<AnnonceResponseDTO> page = annonceService.findAllByRegionCode(regionCode, pageable);
        PageResponseDTO<AnnonceResponseDTO> response = new PageResponseDTO<>(page);
        return ResponseEntity.ok(response);
    }


    

}

        /*
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnnonceResponseDTO> updateAnnonce(
            @PathVariable Long id,
            @Valid @ModelAttribute AnnonceRequestDTO requestDTO,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @AuthenticationPrincipal UserDetails userDetails) {

        try {
            User user = userService.getUserEntity(userDetails);
            AnnonceResponseDTO response = annonceService.updateAnnonce(id, requestDTO, user, imageFile);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("Erreur de validation: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            log.error("Erreur technique lors de la mise à jour", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserEntity(userDetails);
        annonceService.deleteAnnonce(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<AnnonceResponseDTO> validateAnnonce(@PathVariable Long id) {
        AnnonceResponseDTO response = annonceService.validerAnnonce(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/refuser")
    public ResponseEntity<AnnonceResponseDTO> refuseAnnonce(@PathVariable Long id) {
        AnnonceResponseDTO response = annonceService.refuserAnnonce(id);
        return ResponseEntity.ok(response);
    }
}   */