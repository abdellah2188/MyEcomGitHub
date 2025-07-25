
package com.hamch.orderserviceb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
    private JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	 System.out.println("TTTTTTTTTTTTTvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        return http
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(ar->ar.requestMatchers( "/api/order/**").permitAll())
                .authorizeHttpRequests(ar->ar.requestMatchers("/**").hasAuthority("USER"))
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                .oauth2ResourceServer((ors->ors.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter))))
                .headers(h->h.frameOptions(fo->fo.disable()))
                .csrf(csrf->csrf.ignoringRequestMatchers("/h2-console/**"))
                .build();
    }
    
    private static JwtAuthenticationConverter jwtAuthenticationConverter()
    {
   //   	 System.out.println("fffffffffffffffffffffffffffff");

        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("resource_access.user.roles");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
   
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
      	 System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        CorsConfiguration configuration = new CorsConfiguration();
     //   configuration.addAllowedOrigin(Arrays.asList("http://localhost:4200"));
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
