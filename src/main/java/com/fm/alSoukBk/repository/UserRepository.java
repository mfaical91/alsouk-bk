package com.fm.alSoukBk.repository;


import com.fm.alSoukBk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByUsernameAndEmail(String username, String email);
    boolean existsByUsername(String username); //todo difference entre les deux find and existe
}
