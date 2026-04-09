package com.fm.alSoukBk.service;

import com.fm.alSoukBk.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    User getUserEntity(UserDetails userDetails);
    User register(String name,String email, String password);
    User login(String email, String password);
}
