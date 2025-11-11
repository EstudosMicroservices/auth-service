package com.microservices.auth.application.exceptions.custom.token;

import com.microservices.auth.application.exceptions.BaseException;
import org.springframework.http.HttpStatusCode;

public class TokenDoesNotExistsException extends BaseException {

    public TokenDoesNotExistsException(String detail) {
        super(HttpStatusCode.valueOf(204), "Token does not exist!", detail);
    }
}