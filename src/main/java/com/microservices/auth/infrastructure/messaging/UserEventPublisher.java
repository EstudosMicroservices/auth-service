package com.microservices.auth.infrastructure.messaging;

import com.microservices.auth.application.dto.events.UserRegisteredEvent;
import com.microservices.auth.infrastructure.config.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(UserEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    public void publishUserRegistered(UserRegisteredEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.USER_REGISTERED_EXCHANGE,
                RabbitMQConfig.USER_REGISTERED_ROUTING_KEY,
                event
            );
            logger.info("Published UserRegisteredEvent for user ID: {}", event.getId());
        } catch (Exception e) {
            logger.error("Failed to publish UserRegisteredEvent for user ID: {}. Error: {}", event.getId(), e.getMessage(), e);
        }
    }
}