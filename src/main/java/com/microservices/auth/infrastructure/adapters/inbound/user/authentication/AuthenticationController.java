package com.microservices.auth.infrastructure.adapters.inbound.user.authentication;

import com.microservices.auth.application.dto.user.UserAuthenticationDto;
import com.microservices.auth.application.dto.user.UserRegistrationDto;
import com.microservices.auth.application.mappers.AuthUserMapper;
import com.microservices.auth.domain.model.user.UserAuthDomain;
import com.microservices.auth.domain.model.user.UserRegistrationDomain;
import com.microservices.auth.domain.ports.inbound.user.authentication.UserAuthenticatePort;
import com.microservices.auth.domain.ports.inbound.user.authentication.UserLogoutPort;
import com.microservices.auth.domain.ports.inbound.user.authentication.UserRegisterPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Requisições de autenticação")
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserAuthenticatePort userAuthenticatePort;
    private final UserLogoutPort userLogoutPort;
    private final UserRegisterPort userRegisterPort;
    private final AuthUserMapper authUserMapper;

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody UserAuthenticationDto userAuthenticationDto){
        UserAuthDomain toAuthenticate = authUserMapper.toAuthDomain(userAuthenticationDto);
        String token = userAuthenticatePort.authenticate(toAuthenticate);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated() ) {
            return ResponseEntity.badRequest().build();
        }
        String token = authentication.getCredentials().toString();
        userLogoutPort.logout(token);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDto registrationDto) {
        UserRegistrationDomain userToRegister = authUserMapper.toRegisterDomain(registrationDto);
        String token = userRegisterPort.registerUser(userToRegister);
        return ResponseEntity.ok().body(token);
    }

}
