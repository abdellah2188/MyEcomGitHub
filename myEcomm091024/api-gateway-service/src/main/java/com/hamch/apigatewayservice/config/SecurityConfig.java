
  package com.hamch.apigatewayservice.config;
  
  import java.util.Arrays;
  
  import org.springframework.context.annotation.Bean; 
  import  org.springframework.context.annotation.Configuration; 
  import  org.springframework.security.config.Customizer; 
  import  org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; 
  import  org.springframework.security.config.annotation.web.builders.HttpSecurity;
  import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
  import  org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity; 
  import  org.springframework.security.config.web.server.ServerHttpSecurity ;
  //import  org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler; 
  //import  org.springframework.security.oauth2.client.registration.ClientRegistrationRepository; 
  import  org.springframework.security.web.SecurityFilterChain; 
  import  org.springframework.web.cors.CorsConfiguration; 
  import  org.springframework.web.cors.CorsConfigurationSource; 
  import  org.springframework.web.cors.UrlBasedCorsConfigurationSource;
  
  
  @Configuration
  
  @EnableWebSecurity
  @EnableMethodSecurity(prePostEnabled = true)
  public class SecurityConfig {
  
	  private JwtAuthConverter jwtAuthConverter; 
	  public  SecurityConfig(JwtAuthConverter jwtAuthConverter) {  this.jwtAuthConverter =  jwtAuthConverter; }
	  
	  @Bean 
	  public SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception{ 
		  return http 
				  .csrf(Customizer.withDefaults())
				  .authorizeHttpRequests(ar->ar.requestMatchers("/**").permitAll())
				  .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
				  .oauth2ResourceServer(o2->o2.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter))) 
				  .headers(h->h.frameOptions(fo->fo.disable())) 
				  .cors(cors-> cors.configurationSource(corsConfigurationSource())) 
				  .build(); 
}
  
  
  @Bean 
  CorsConfigurationSource corsConfigurationSource() { 
	  CorsConfiguration  configuration = new CorsConfiguration();
	  //configuration.setAllowedOrigins(Arrays.asList("*"));
      configuration.applyPermitDefaultValues();
	  configuration.setAllowedMethods(Arrays.asList("*"));
	  configuration.setAllowedHeaders(Arrays.asList("*"));
	  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
	  source.registerCorsConfiguration("/**",  configuration); 
  return source; 
  }
  
  }
 