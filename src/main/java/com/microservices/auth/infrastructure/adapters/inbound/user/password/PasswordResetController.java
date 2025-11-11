package com.microservices.auth.infrastructure.adapters.inbound.user.password;

import com.microservices.auth.domain.ports.inbound.user.password.RequestResetPasswordPort;
import com.microservices.auth.domain.ports.inbound.user.password.ResetPasswordPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Password", description = "Requisições de redefinição de senha")
@RestController
@RequestMapping("password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final RequestResetPasswordPort requestPasswordReset;
    private final ResetPasswordPort resetPasswordPort;

    @PostMapping("/request/password-change")
    public ResponseEntity<Void> requestPasswordReset(@RequestParam String email){
        requestPasswordReset.requestPasswordReset(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change")
    public ResponseEntity<Void> resetPassword(@RequestParam String token, @RequestParam String newPassword){
        resetPasswordPort.resetPassword(token, newPassword);
        return ResponseEntity.ok().build();
    }
}
