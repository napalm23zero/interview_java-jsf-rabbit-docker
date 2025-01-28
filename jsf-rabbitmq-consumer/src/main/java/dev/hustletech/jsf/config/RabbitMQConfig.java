package dev.hustletech.jsf.config;

import com.rabbitmq.client.ConnectionFactory;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import lombok.Getter;

@Named
@ApplicationScoped
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "jsfQueue";

    @Getter
    private ConnectionFactory factory;

    @PostConstruct
    public void init() {
        System.out.println("🚀 Initializing RabbitMQConfig");
        try {
            factory = new ConnectionFactory();
            factory.setHost("rabbitmq");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            System.out.println("✅ RabbitMQ Configuration completed");
        } catch (Exception e) {
            System.err.println("❌ RabbitMQ Configuration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Produces
    public ConnectionFactory getConnectionFactory() {
        return factory;
    }
}