package com.hamch.orderserviceb;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
/////////////////import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RequiredArgsConstructor
public class OrderServicebApplication {
    private final BeanFactory beanFactory;
    public static void main(String[] args) {
        System.out.println("11111111111");
        SpringApplication.run(OrderServicebApplication.class, args);
    }
/*

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        System.out.println("22222222222");
        return requestTemplate -> {
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext()
                    .getAuthentication();
            requestTemplate.header("Authorization", "Bearer " + token.getToken().getTokenValue());
        };
    }
*/

    /*@Bean
    public ExecutorService traceableExecutorService() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        return new TraceableExecutorService(beanFactory, executorService);
    }*/
}
