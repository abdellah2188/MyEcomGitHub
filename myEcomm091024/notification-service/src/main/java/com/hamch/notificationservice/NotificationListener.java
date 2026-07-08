/* package com.hamch.notificationservice;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotificationListener {

    // Écoute directement la file d'attente physique de RabbitMQ
    @RabbitListener(queues = "order-notification-exchange.notification-service-group")
    public void receiveOrder(Message message) {
        log.info("=========================================");
        log.info("🔥 VICTOIRE : MESSAGE ENFIN CAPTURÉ !!! 🔥");
        log.info("=========================================");

        try {
            // Lecture sécurisée des octets bruts envoyés par l'émetteur
            byte[] body = message.getBody();
            String jsonText = new String(body);

            log.info("Données JSON de la commande : {}", jsonText);
        } catch (Exception e) {
            log.error("Erreur lors du décodage du message", e);
        }
    }
}
 */