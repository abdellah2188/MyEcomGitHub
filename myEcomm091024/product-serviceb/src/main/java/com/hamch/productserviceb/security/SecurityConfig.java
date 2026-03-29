package com.hamch.productserviceb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
    private final JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	System.out.println("TTTTTTTTTTTTTvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        return http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(ar->ar.requestMatchers("/products/images/**","/**", "/api/product/**","/api/product/photoProduct/**").permitAll())
                .authorizeHttpRequests(ar->ar.requestMatchers("/**","/products/**").hasAuthority("USER"))
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                .oauth2ResourceServer((ors->ors.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter))))
                .headers(h->h.frameOptions(fo->fo.disable()))
                .csrf(csrf->csrf.ignoringRequestMatchers("/h2-console/**"))
                .build();
    }
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//      	 System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//
//        CorsConfiguration configuration = new CorsConfiguration();
//     //   configuration.addAllowedOrigin(Arrays.asList("http://localhost:4200"));
//        //configuration.addAllowedOrigin("http://localhost:4200");
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    


}