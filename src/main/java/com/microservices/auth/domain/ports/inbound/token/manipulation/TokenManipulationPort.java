package com.microservices.auth.domain.ports.inbound.token.manipulation;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface TokenManipulationPort {

    String extractUsername(String token);

    boolean isTokenExpired(String token);

    boolean validateToken(String token, UserDetails userDetails);

    Date extractExpiration(String token);
}
