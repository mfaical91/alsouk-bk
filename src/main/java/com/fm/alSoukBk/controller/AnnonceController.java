package com.fm.alSoukBk.controller;


import com.fm.alSoukBk.model.Annonce;
import com.fm.alSoukBk.model.User;
import com.fm.alSoukBk.service.AnnonceService;
import com.fm.alSoukBk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/annonces")
@RequiredArgsConstructor
public class AnnonceController {

    private final AnnonceService annonceService;
    private final UserService userService;

    // ✅ Ajouter une annonce
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Annonce> addAnnonce(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam String location,
            @RequestParam String category,
            @RequestParam("image") MultipartFile imageFile,
            @AuthenticationPrincipal UserDetails user
    ) throws IOException {

       try{ // Génère un nom de fichier unique
        String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path filepath = Paths.get("uploads/", filename);
        Files.createDirectories(filepath.getParent());
        Files.write(filepath, imageFile.getBytes());

        Annonce annonce = new Annonce();
        annonce.setTitle(title);
        annonce.setDescription(description);
        annonce.setPrice(price);
        annonce.setLocation(location);
        annonce.setCategory(category);
        annonce.setImageUrl("/uploads/" + filename); // à afficher plus tard

        User userEntity = userService.getUserEntity(user);
        annonce.setUser(userEntity);

        Annonce saved = annonceService.createAnnonce(annonce);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
       } catch (IOException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(null);
       }
    }



    // ✅ Récupérer toutes les annonces
    @GetMapping
    public List<Annonce> getAllAnnonces() {
        return annonceService.getAllAnnonces();
    }

    // ✅ Récupérer une annonce par ID
    @GetMapping("/{id}")
    public ResponseEntity<Annonce> getAnnonceById(@PathVariable Long id) {
        Annonce annonce = annonceService.getAnnonceById(id);
        return ResponseEntity.ok(annonce);
    }

    // ✅ Mettre à jour une annonce
    @PutMapping("/{id}")
    public ResponseEntity<Annonce> updateAnnonce(@PathVariable Long id, @RequestBody Annonce updatedAnnonce) {
        Annonce annonce = annonceService.updateAnnonce(id, updatedAnnonce);
        return ResponseEntity.ok(annonce);
    }

    // ✅ Supprimer une annonce
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        annonceService.deleteAnnonce(id);
        return ResponseEntity.noContent().build();
    }


    // ✅ Validation
    @PutMapping("/{id}/valider")
    public ResponseEntity<Annonce> validerAnnonce(@PathVariable Long id) {
        Annonce annonce = annonceService.validerAnnonce(id);
        return ResponseEntity.ok(annonce);
    }

    // ❌ Refus
    @PutMapping("/{id}/refuser")
    public ResponseEntity<Annonce> refuserAnnonce(@PathVariable Long id) {
        Annonce annonce = annonceService.refuserAnnonce(id);
        return ResponseEntity.ok(annonce);
    }

}
