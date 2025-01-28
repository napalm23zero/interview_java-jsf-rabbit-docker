package dev.hustletech.jsf.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import dev.hustletech.jsf.config.RabbitMQConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.java.Log;

@Log
@ApplicationScoped
public class RabbitMQService {

    @Inject
    private RabbitMQConfig config;

    public void publishMessage(String message) {
        try (Connection connection = config.getFactory().newConnection();
                Channel channel = connection.createChannel()) {

            channel.queueDeclare(RabbitMQConfig.QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", RabbitMQConfig.QUEUE_NAME, null, message.getBytes());

            log.info(() -> String.format("✨ Message sent to RabbitMQ: %s", message));
        } catch (Exception e) {
            log.severe(() -> String.format("💥 Failed to publish message: %s", e.getMessage()));
            throw new RuntimeException("Failed to publish message to RabbitMQ", e);
        }
    }
}