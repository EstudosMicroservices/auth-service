package com.microservices.auth.infrastructure.adapters.inbound.verification;

import com.microservices.auth.domain.ports.inbound.token.authentication.verify.AuthTokenVerifyPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Verification", description = "Verificação de tokens")
@RestController
@RequestMapping("verify")
@RequiredArgsConstructor
public class VerificationController {

    private final AuthTokenVerifyPort authTokenVerifyPort;

    @GetMapping("/session")
    public ResponseEntity<String> verifySession(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido ou sessão expirada.");
        }
        String username = authentication.getName();

        try {
            authTokenVerifyPort.verifySession(authentication);
            return ResponseEntity.ok("Sessão válida para o usuário: " + username);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Sessão inválida: " + e.getMessage());
        }
    }


    @PostMapping("/tokenByEmail/{email}")
    public ResponseEntity<Void> verifyTokenWithEmail(@PathVariable String email){
        authTokenVerifyPort.verifyToken(email);
        return ResponseEntity.ok().build();
    }
}
