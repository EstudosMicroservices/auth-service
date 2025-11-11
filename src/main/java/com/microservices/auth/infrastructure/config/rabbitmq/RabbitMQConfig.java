package com.microservices.auth.infrastructure.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String USER_REGISTERED_EXCHANGE = "user.registered.exchange";
    public static final String USER_PROFILE_QUEUE = "user.profile.queue";
    public static final String USER_REGISTERED_ROUTING_KEY = "user.registered.key";

    @Bean
    public TopicExchange userRegisteredExchange() {
        return new TopicExchange(USER_REGISTERED_EXCHANGE);
    }

    @Bean
    public Queue userRegisteredQueue() {
        return new Queue(USER_PROFILE_QUEUE, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding bindingUserRegistered(Queue userRegisteredQueue, TopicExchange userRegisteredExchange) {
        return BindingBuilder.bind(userRegisteredQueue)
                .to(userRegisteredExchange)
                .with(USER_REGISTERED_ROUTING_KEY);
    }
}