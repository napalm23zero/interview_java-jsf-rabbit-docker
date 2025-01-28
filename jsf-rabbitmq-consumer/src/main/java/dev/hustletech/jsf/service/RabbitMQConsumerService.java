package dev.hustletech.jsf.service;

import com.rabbitmq.client.*;
import dev.hustletech.jsf.config.RabbitMQConfig;
import dev.hustletech.jsf.websocket.MessageWebSocket;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Log
@ApplicationScoped
public class RabbitMQConsumerService {

    @Inject
    private RabbitMQConfig config;

    @Inject
    private MessageWebSocket webSocket;

    private Connection connection;
    private Channel channel;

    @Getter
    private boolean connected;

    @PostConstruct
    public void init() {
        System.out.println("🚀 Initializing RabbitMQ Consumer");
        startConsumer();
    }

    private void startConsumer() {
        try {
            System.out.println("1️⃣ Creating connection...");
            connection = config.getFactory().newConnection();

            System.out.println("2️⃣ Creating channel...");
            channel = connection.createChannel();

            System.out.println("3️⃣ Declaring queue: " + RabbitMQConfig.QUEUE_NAME);
            // Make queue durable
            channel.queueDeclare(RabbitMQConfig.QUEUE_NAME, true, false, false, null);

            System.out.println("4️⃣ Setting up consumer...");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("📥 Received message: " + message);
                try {
                    webSocket.broadcastMessage(message);
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    System.out.println("✅ Message processed and acknowledged");
                } catch (Exception e) {
                    System.err.println("❌ Error processing message: " + e.getMessage());
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                }
            };

            // Set prefetch count to 1
            channel.basicQos(1);

            System.out.println("5️⃣ Starting consumer...");
            channel.basicConsume(
                    RabbitMQConfig.QUEUE_NAME,
                    false, // manual ack
                    deliverCallback,
                    consumerTag -> System.out.println("⚠️ Consumer cancelled: " + consumerTag));

            connected = true;
            System.out.println("✅ Consumer ready and listening!");

        } catch (Exception e) {
            System.err.println("❌ Consumer startup failed: " + e.getMessage());
            e.printStackTrace();
            scheduleReconnect();
        }
    }

    private void reconnect() {
        connected = false;
        cleanup();
        scheduleReconnect();
    }

    private void scheduleReconnect() {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                log.info("🔄 Attempting to reconnect...");
                startConsumer();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
            connected = false;
            log.info("✅ RabbitMQ connections closed successfully");
        } catch (IOException | TimeoutException e) {
            log.severe("❌ Error closing connections: " + e.getMessage());
        }
    }

    public boolean isConnected() {
        return connected && connection != null && connection.isOpen();
    }
}