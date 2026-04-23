//
//package com.hamch.apigatewayservice.config;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//    	
//    	System.out.println("TTTTTTTTTTTTTxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//        return http.authorizeHttpRequests(authorize -> authorize
//        		              .requestMatchers("/**").permitAll()
//        		              .anyRequest().authenticated())
//        		   .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//        		   .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//        	       .build();
//
//    }
//    
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//    	CorsConfiguration configuration = new CorsConfiguration();
//    	configuration.applyPermitDefaultValues();
//    	 configuration.setAllowedOrigins(Arrays.asList("*"));
//    	configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS","HEAD"));
//    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    	source.registerCorsConfiguration("/**", configuration);
//    	return source;
//    	
//    }
//   
//}



