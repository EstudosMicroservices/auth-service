package com.microservices.auth.infrastructure.config;

import com.microservices.auth.application.services.AuthServiceImpl;
import com.microservices.auth.application.services.TokenServiceImpl;
import com.microservices.auth.application.usecaseimpl.AuthUseCaseImpl;
import com.microservices.auth.application.usecaseimpl.TokenUseCaseImpl;
import com.microservices.auth.domain.ports.inbound.TokenServicePort;
import com.microservices.auth.domain.ports.outbound.TokenRepositoryPort;
import com.microservices.auth.infrastructure.adapters.outbound.TokenAdapter;
import com.microservices.auth.infrastructure.client.UserClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public AuthServiceImpl authServiceImpl(AuthUseCaseImpl authUseCaseImpl){
        return new AuthServiceImpl(authUseCaseImpl);
    }

    @Bean
    public TokenServiceImpl tokenServiceImpl(TokenUseCaseImpl tokenUseCaseImpl){
        return new TokenServiceImpl(tokenUseCaseImpl);
    }

    @Bean
    public AuthUseCaseImpl authUseCaseImpl(TokenServiceImpl tokenServiceImpl, TokenRepositoryPort tokenRepositoryPort, PasswordEncoder passwordEncoder,
                                           UserClient userCLient){
        return new AuthUseCaseImpl(tokenServiceImpl, tokenRepositoryPort, passwordEncoder, userCLient);
    }

    @Bean
    public TokenUseCaseImpl tokenUseCaseImpl(@Value("${jjwt.secret}") String jjwtSecret, TokenRepositoryPort tokenRepositoryPort) {
        return new TokenUseCaseImpl(jjwtSecret, tokenRepositoryPort);
    }


    @Bean
    public TokenAdapter tokenAdapter(StringRedisTemplate stringRedisTemplate){
        return new TokenAdapter(stringRedisTemplate);
    }

    @Bean
    public TokenRepositoryPort tokenRepositoryPort(TokenAdapter tokenAdapter){
        return tokenAdapter;
    }

    @Bean
    public TokenServicePort tokenServicePort(TokenServiceImpl tokenServiceImpl){
        return tokenServiceImpl;
    }


}
