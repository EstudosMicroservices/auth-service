package com.microservices.auth.infrastructure.adapters.inbound.admin;

import com.microservices.auth.domain.ports.outbound.token.authentication.query.AuthTokenQueryPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Tag(name = "Admin", description = "Requisições de Administradores")
@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthTokenQueryPort authTokenQueryPort;

    @GetMapping("/active-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<String>> getActiveUsers() {
        Set<String> activeUserEmails = authTokenQueryPort.getAllActiveUserIds();
        return ResponseEntity.ok(activeUserEmails);
    }
}
