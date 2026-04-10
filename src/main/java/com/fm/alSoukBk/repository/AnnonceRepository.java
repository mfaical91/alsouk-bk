package com.fm.alSoukBk.repository;


import com.fm.alSoukBk.model.Annonce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

    Page<Annonce> findByRegionCode(String regionCode, Pageable pageable);
}