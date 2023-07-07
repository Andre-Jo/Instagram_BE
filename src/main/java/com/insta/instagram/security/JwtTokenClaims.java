package com.insta.instagram.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class JwtTokenClaims {
    private String username;
}
