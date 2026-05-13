package com.fm.alSoukBk.controller;

import com.fm.alSoukBk.dto.AnnonceRequestDTO;
import com.fm.alSoukBk.dto.AnnonceResponseDTO;
import com.fm.alSoukBk.dto.PageResponseDTO;
import com.fm.alSoukBk.model.User;
import com.fm.alSoukBk.service.AnnonceService;
import com.fm.alSoukBk.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/annonces")
public class AnnonceController {

    private final AnnonceService annonceService;
    private final UserService   userService;

    public AnnonceController(AnnonceService annonceService, UserService userService) {
        this.annonceService = annonceService;
        this.userService = userService;
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
    @PutMapping("/{id}")
    public ResponseEntity<AnnonceResponseDTO> updateAnnonce(
            @PathVariable Long id,
            @RequestBody AnnonceRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserEntity(userDetails);
        return ResponseEntity.ok(annonceService.updateAnnonce(id, dto, user));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserEntity(userDetails);

        annonceService.deleteAnnonceByOwner(id, user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<AnnonceResponseDTO>> getMyAnnonces(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.getUserEntity(userDetails);

        return ResponseEntity.ok(annonceService.findByUser(user));
    }
}

