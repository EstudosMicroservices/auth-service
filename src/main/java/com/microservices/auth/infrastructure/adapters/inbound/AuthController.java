package com.microservices.auth.infrastructure.adapters.inbound;

import com.microservices.auth.application.dto.AuthRequestDto;
import com.microservices.auth.application.dto.SessionDto;
import com.microservices.auth.application.services.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<SessionDto> login (@RequestBody AuthRequestDto authRequestDto){
        String token = authServiceImpl.authenticate(authRequestDto);
        return ResponseEntity.ok().body(new SessionDto(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout (@RequestBody SessionDto sessionDto){
        return ResponseEntity.ok().body(authServiceImpl.logout(sessionDto));
    }

    @PostMapping("/session")
    public ResponseEntity<Void> verifySession(@RequestHeader(name="x-auth", required = true) String request){
        authServiceImpl.verifySession(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/validation/{id}")
    public ResponseEntity<Void> verifyTokenWithId(@PathVariable String id){
        authServiceImpl.verifyTokenWithId(id);
        return ResponseEntity.ok().build();
    }
}
