package com.microservices.auth.infrastructure.client;

import com.microservices.auth.application.dto.user.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class UserWebClient {

    private final Logger log = LoggerFactory.getLogger(UserWebClient.class);
    private final WebClient webClient;

    public UserWebClient(@Value("${user.service.url}") String userServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(userServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Optional<UserDto> findByEmail(String email) {
        log.info("Checking if user with email {} exists in user-service", email);
        try {
            return Optional.ofNullable(
                    webClient.get()
                            .uri(uriBuilder -> uriBuilder.path("/user-profile/findByEmail")
                                    .queryParam("email", email)
                                    .build())
                            .retrieve()
                            .onStatus(
                                    status -> status.value() == 404,
                                    clientResponse -> Mono.just(new RuntimeException("User not found: " + email))
                            )
                            .onStatus(
                                    HttpStatusCode::isError,
                                    this::handleErrorResponse
                            )
                            .bodyToMono(UserDto.class)
                            .block()
            );
        } catch (Exception e) {
            log.error("Failed to check user existence for email {}: {}", email, e.getMessage());
            return Optional.empty();
        }
    }

    private Mono<RuntimeException> handleErrorResponse(org.springframework.web.reactive.function.client.ClientResponse response) {
        return response.bodyToMono(String.class)
                .map(errorBody -> {
                    log.error("Error body: {}", errorBody);
                    return new RuntimeException("Error from user-service: " + errorBody);
                });
    }
}
