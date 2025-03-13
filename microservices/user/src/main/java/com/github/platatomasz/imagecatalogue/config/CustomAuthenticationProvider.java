package com.github.platatomasz.imagecatalogue.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        throw new InsufficientAuthenticationException("Dummy");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
