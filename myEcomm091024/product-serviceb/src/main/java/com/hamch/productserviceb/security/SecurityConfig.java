package com.hamch.productserviceb.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	
	
	private JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

	/*
	 * public SecurityConfig(ClientRegistrationRepository
	 * clientRegistrationRepository) { this.clientRegistrationRepository =
	 * clientRegistrationRepository; }
	 */
	
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(ar->ar.requestMatchers("/**").permitAll())
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                .oauth2ResourceServer(o2->o2.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .headers(h->h.frameOptions(fo->fo.disable()))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .build();
    }
	
	/*
	 * private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
	 * final OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new
	 * OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);
	 * oidcLogoutSuccessHandler.setPostLogoutRedirectUri(
	 * "{baseUrl}?logoutsuccess=true"); return oidcLogoutSuccessHandler; }
	 */
	
	 @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("*"));
	        configuration.setAllowedHeaders(Arrays.asList("*"));
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

}
