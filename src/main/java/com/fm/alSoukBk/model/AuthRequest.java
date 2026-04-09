package com.fm.alSoukBk.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Setter
@Getter
public class AuthRequest {
    private String email;
    private String password;

}
