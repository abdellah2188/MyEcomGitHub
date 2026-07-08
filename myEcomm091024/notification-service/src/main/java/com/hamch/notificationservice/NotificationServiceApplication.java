package com.hamch.notificationservice;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableCaching
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

 // --- DÉCLARATION FORCÉE DES COMPOSANTS PHYSIQUES RABBITMQ ---
    @Bean(name = "processOrderNotification")
    public Function<Flux<Message<byte[]>>, Mono<Void>> processOrderNotification() {
        return flux -> flux
            .doOnNext(message -> {
                log.info("=========================================");
                log.info("🎉 VICTOIRE FINALE : LE FLUX RÉACTIF EST CAPTÉ !");
                log.info("=========================================");

                try {
                    byte[] payload = message.getPayload();
                    String jsonText = new String(payload);
                    log.info("Données JSON reçues de la commande : {}", jsonText);
                } catch (Exception e) {
                    log.error("Erreur lors du décodage du message réactif", e);
                }
            })
            .then(); // Permet de vider le flux proprement sans renvoyer de données
    }

    
}


