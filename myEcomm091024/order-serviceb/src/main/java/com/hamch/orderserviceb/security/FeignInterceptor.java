package com.hamch.orderserviceb.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class FeignInterceptor implements RequestInterceptor {
    /*  @Override
    public void apply(RequestTemplate requestTemplate) {
    
        //     System.out.printf("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUu");
    
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String jwtAccessToken = jwtAuthenticationToken.getToken().getTokenValue();
        requestTemplate.header("Authorization", "Bearer " + jwtAccessToken);
    
        //    System.out.printf("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUu"+ jwtAccessToken);
    } */

    @Override
    public void apply(RequestTemplate template) {
        String token = null;

        // 1. Tente d'abord de récupérer via Spring Security (si thread d'origine)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            token = jwtAuthenticationToken.getToken().getTokenValue();
        }

        // 2. Si null, récupère le jeton extrait depuis notre conteneur inter-thread
        if (token == null) {
            token = JwtContextHolder.getToken();
        }

        // 3. Injecte le jeton dans l'en-tête de la requête Feign
        if (token != null) {
            template.header("Authorization", "Bearer " + token);
        }
    }
}