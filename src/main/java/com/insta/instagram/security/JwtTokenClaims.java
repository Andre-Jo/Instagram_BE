package com.insta.instagram.security;

import lombok.Data;

@Data
public class JwtTokenClaims {
    private String username;
}
