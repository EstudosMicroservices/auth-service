package com.microservices.auth.infrastructure.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.microservices.auth.domain.ports.inbound.token.manipulation.TokenManipulationPort;
import com.microservices.auth.domain.ports.outbound.token.authentication.query.AuthTokenQueryPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final AuthTokenQueryPort authTokenQueryPort;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final TokenManipulationPort tokenManipulationPort;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        logger.debug("Starting JwtAuthenticationFilter for URI: {}", request.getRequestURI());
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        String userEmail;

        try {
            if (authTokenQueryPort.isActiveToken(jwt) || authTokenQueryPort.isTokenBlacklisted(jwt)) {
                logger.warn("Token not active or blacklisted for URI: {}", request.getRequestURI());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou blacklisted");
                return;
            }

            userEmail = tokenManipulationPort.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (tokenManipulationPort.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            jwt,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("User {} authenticated successfully", userEmail);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                    return;
                }
            }

            filterChain.doFilter(request, response); // continua apenas se token válido

        } catch (JWTVerificationException e) {
            logger.warn("JWT verification failed for URI {}: {}", request.getRequestURI(), e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
        } catch (Exception e) {
            logger.error("Unexpected error processing JWT for URI {}: {}", request.getRequestURI(), e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno do servidor");
        }
    }
}