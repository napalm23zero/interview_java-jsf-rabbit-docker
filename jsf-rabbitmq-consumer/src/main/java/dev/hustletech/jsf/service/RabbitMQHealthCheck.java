package dev.hustletech.jsf.service;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Readiness
public class RabbitMQHealthCheck implements HealthCheck {

    @Inject
    private RabbitMQConsumerService consumerService;

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("rabbitmq")
                .status(consumerService.isConnected())
                .build();
    }
}