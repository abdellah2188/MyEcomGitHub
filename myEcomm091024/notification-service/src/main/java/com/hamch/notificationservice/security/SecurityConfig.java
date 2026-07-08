package com.hamch.notificationservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    // Injection par constructeur (plus robuste)
    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
            .cors(Customizer.withDefaults())
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/actuator/**").permitAll()
                //.pathMatchers("/**").hasAnyAuthority("USER")
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(ors -> ors
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)) // PAS d'adaptateur ici
            )
            .headers(h -> h.frameOptions(fo -> fo.disable()))
            .build();
    }
}