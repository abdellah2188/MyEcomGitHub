package com.hamch.customerservice.security;

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
                .authorizeHttpRequests(ar->ar.requestMatchers("/**").hasAuthority("USER"))
                .authorizeHttpRequests(ar->ar.requestMatchers("/api/customer/**").hasAuthority("USER"))
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                .oauth2ResourceServer((ors->ors.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter))))
                .headers(h->h.frameOptions(fo->fo.disable()))
                .csrf(csrf->csrf.ignoringRequestMatchers("/h2-console/**"))
                .build();
    }

    /* @Bean
    public JwtDecoder jwtDecoder() {

                System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM111111");

        return NimbusJwtDecoder.withJwkSetUri("http://localhost:7080/realms/microservices-realm/protocol/openid-connect/certs").build();
    } */

    /* @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
}  */
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//      	 System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//
//        CorsConfiguration configuration = new CorsConfiguration();
//     //   configuration.addAllowedOrigin(Arrays.asList("http://localhost:4200"));
//        //configuration.addAllowedOrigin("http://localhost:4200");
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    


}