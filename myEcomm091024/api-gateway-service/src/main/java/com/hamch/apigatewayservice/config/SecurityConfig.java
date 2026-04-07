/*
package com.hamch.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(exchange -> exchange.anyExchange().authenticated())
           .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
      //   .oauth2Login(withDefaults());

        http.cors().disable();
        http.csrf().disable();
        return http.build();

    }
    //@Bean
    */
/*@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();
        http.csrf().disable();
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/**","/register/**", "/api/product/**").permitAll();
        //http.authorizeRequests().antMatchers("/appUser/**","/appRole/**").hasAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        System.out.println("security4444444444444444444444444444444");

    }*//*

}
*/
