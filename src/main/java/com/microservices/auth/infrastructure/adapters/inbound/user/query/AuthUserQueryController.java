package com.microservices.auth.infrastructure.adapters.inbound.user.query;

import com.microservices.auth.application.dto.user.UserAuthenticationDto;
import com.microservices.auth.application.implementations.user.query.FindAllAuthUsersImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Authentication Query", description = "Requisições de consulta de usuários autenticados")
@RestController
@RequestMapping("auth-user-query")
@RequiredArgsConstructor
public class AuthUserQueryController {

    private final FindAllAuthUsersImpl findAllAuthUsers;

    @GetMapping("findAll")
    public ResponseEntity<List<UserAuthenticationDto>> findAll(){
        return ResponseEntity.ok().body(findAllAuthUsers.findAll());
    }
}
