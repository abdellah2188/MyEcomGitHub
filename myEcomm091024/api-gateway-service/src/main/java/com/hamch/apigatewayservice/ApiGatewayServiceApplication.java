package com.hamch.apigatewayservice;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
//@SpringBootApplication(scanBasePackages = "org.springframework.security.oauth2.jwt")
//@ComponentScan("com.hamch.apigatewayservice.config")
//@ComponentScan("com.hamch.apigatewayservice.config.SecurityConfig.class")
//@Configurable
public class ApiGatewayServiceApplication {
    public static void main(String[] args) {
    	
//    	HttpClient client = HttpClient.create().resolver(spec -> spec.queryTimeout(Duration.ofMillis(2000)));
//    	
//    	String response = client.get()
//    			.uri("127.0.0.1:8080/")
//    		//	.uri("http://127.0.0.1:4200/")
//    			//.uri("M-A-HAMCH.mshome.net")
//    			//.uri("127.0.0.1:8080")
//    			.responseContent()
//    			.aggregate()
//    			.asString()
//    			.block();
    			
        SpringApplication.run(ApiGatewayServiceApplication.class, args);
        
       
    }

    
    
    @Bean
    DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc,
                                                        DiscoveryLocatorProperties dlp){
        return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
    }

    
    
    @Bean
    public CorsWebFilter corsWebFilter() {

        final CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));

        corsConfig.setMaxAge(36000L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST","PUT","DELETE"));
        corsConfig.addAllowedHeader("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
    /*@Bean
    public RouteLocator theRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth", r ->
                        r.path("/auth/**")
                                .filters(f -> f.rewriteResponseHeader("Referrer-Policy", "no-referrer", "same-origin"))
                                .uri("https://keycloak"))
                .build();
    }*/
}
