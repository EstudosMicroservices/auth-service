package com.microservices.auth.infrastructure.client;

import com.microservices.auth.application.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8081", name = "userClient")
public interface UserClient {

    @GetMapping("User/findBy/id/{id}")
    public ResponseEntity<UserDto> findById(@Valid @PathVariable String id);

}
