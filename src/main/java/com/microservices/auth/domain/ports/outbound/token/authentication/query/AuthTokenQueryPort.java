package com.microservices.auth.domain.ports.outbound.token.authentication.query;

import java.util.Set;

public interface AuthTokenQueryPort {

    String getTokenById(String userId);

    boolean isTokenWithIdStored(String userId);

    boolean isTokenBlacklisted(String token);

    boolean isActiveToken(String token);

    Set<String> getAllActiveUserIds();

}
