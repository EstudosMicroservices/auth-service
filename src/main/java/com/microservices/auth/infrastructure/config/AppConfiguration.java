package com.microservices.auth.infrastructure.config;

import com.microservices.auth.domain.model.user.UserAuthDomain;
import com.microservices.auth.domain.ports.outbound.user.AuthUserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class AppConfiguration {

    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepositoryPort authUserRepositoryPort;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserAuthDomain userAuthentication = authUserRepositoryPort.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            Collection<SimpleGrantedAuthority> authorities = userAuthentication.getRoles() != null ?
                    userAuthentication.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.name()))
                            .collect(Collectors.toSet()) :
                    Set.of();

            return new User(
                    userAuthentication.getEmail(),
                    userAuthentication.getSenha(),
                    authorities
            );
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public Random random(){
        return new Random();
    }
}
