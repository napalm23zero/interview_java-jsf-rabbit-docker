package dev.hustletech.jsf;

import com.rabbitmq.client.Connection;
import dev.hustletech.jsf.config.RabbitMQConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class ApplicationStartup {

    @Inject
    private RabbitMQConfig rabbitMQConfig;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        System.out.println("🌟 Application Starting...");

        try {
            // Test RabbitMQ connection
            try (Connection connection = rabbitMQConfig.getFactory().newConnection()) {
                System.out.println("✅ RabbitMQ Connection Test: SUCCESS");
                System.out.println("📬 Queue Name: " + RabbitMQConfig.QUEUE_NAME);
                System.out.println("🔌 Connected to: " + connection.getAddress());
            }
        } catch (Exception e) {
            System.err.println("❌ RabbitMQ Connection Test: FAILED");
            System.err.println("💥 Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}