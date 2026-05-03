package com.fm.alSoukBk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) {

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);

            Files.write(path, file.getBytes());

            String imageUrl = "/uploads/" + fileName;

            return Map.of("imageUrl", imageUrl);

        } catch (IOException e) {
            throw new RuntimeException("Upload failed");
        }
    }
}