package com.sce.platform.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SceAuthenticationToken extends AbstractAuthenticationToken {

    private final SceAuthentication principal;

    public SceAuthenticationToken(
         SceAuthentication principal,
         Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public SceAuthentication getPrincipal() {
        return principal;
    }

}
