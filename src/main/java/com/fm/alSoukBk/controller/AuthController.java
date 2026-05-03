package com.fm.alSoukBk.controller;

import com.fm.alSoukBk.auth.LoginRequest;
import com.fm.alSoukBk.auth.RegisterRequest;
import com.fm.alSoukBk.model.Role;
import com.fm.alSoukBk.model.User;
import com.fm.alSoukBk.payload.JwtResponse;
import com.fm.alSoukBk.repository.UserRepository;
import com.fm.alSoukBk.security.jwt.JwtUtils;
import com.fm.alSoukBk.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getAuthorities()
            ));

        } catch (BadCredentialsException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid credentials"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", "Email is already in use!"));
        }

        // Créer nouveau compte utilisateur
        User user = new User();
             user.setUsername(registerRequest.getUsername());
             user.setEmail(registerRequest.getEmail());
             user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
             Set<Role> roles = new HashSet<>();
             roles.add(new Role(2L,"ROLE_USER"));
             user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully!"));
    }
}
