package dev.hustletech.jsf.config;

import com.rabbitmq.client.ConnectionFactory;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

@ApplicationScoped
public class RabbitMQConfig {

    @Getter
    private ConnectionFactory factory;

    @PostConstruct
    public void init() {
        factory = new ConnectionFactory();
        factory.setHost(System.getenv().getOrDefault("RABBITMQ_HOST", "localhost"));
        factory.setPort(Integer.parseInt(System.getenv().getOrDefault("RABBITMQ_PORT", "5672")));
        factory.setUsername(System.getenv().getOrDefault("RABBITMQ_USERNAME", "guest"));
        factory.setPassword(System.getenv().getOrDefault("RABBITMQ_PASSWORD", "guest"));
    }

    public static final String QUEUE_NAME = System.getenv().getOrDefault("RABBITMQ_QUEUE", "jsfQueue");
}