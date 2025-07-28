package com.hamch.apigatewayservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

@Configuration
//@EnableWebSecurity
@EnableWebFluxSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
    //private JwtAuthConverter jwtAuthConverter;

//    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
//        this.jwtAuthConverter = jwtAuthConverter;
//    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(auth -> auth
        		.pathMatchers("/**").permitAll()
        		.anyExchange().authenticated()
        		)
    //            .oauth2Login(withDefaults())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.cors(Customizer.withDefaults());
        return http.build();
    }
   
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    	
//    	 System.out.println("TTTTTTTTTTTTTvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
//        return http
//                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests(ar->ar.requestMatchers( "/**").hasAuthority("USER"))
////               // .authorizeHttpRequests(ar->ar.requestMatchers("/**").hasAuthority("ADMIN"))
//                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
//                .oauth2ResourceServer((ors->ors.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter))))
//                .headers(h->h.frameOptions(fo->fo.disable()))
//                .cors(cors -> cors.disable())
////               // .csrf(csrf->csrf.ignoringRequestMatchers("/h2-console/**"))
//                .build();
//    }
    
//    @Bean
//    public JwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
//    	
//    	System.out.println("PPPPPRRRRR"+ properties);
//        return JwtDecoders.fromIssuerLocation(properties.getJwt().getIssuerUri());
//    }
//    
//    
    
//    private static JwtAuthenticationConverter jwtAuthenticationConverter()
//    {
//      	 System.out.println("fffffffffffffffffffffffffffff");
//
//        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("resource_access.user.roles");
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//        return jwtAuthenticationConverter;
//    }
   
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
      	 System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        //configuration.addAllowedOrigin("");
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}