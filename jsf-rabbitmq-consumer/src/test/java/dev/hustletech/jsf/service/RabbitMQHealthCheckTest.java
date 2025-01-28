package dev.hustletech.jsf.service;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitMQHealthCheckTest {

    @Mock
    private RabbitMQConsumerService consumerService;

    @InjectMocks
    private RabbitMQHealthCheck healthCheck;

    @Test
    void testHealthCheckWhenConnected() {
        // Arrange
        when(consumerService.isConnected()).thenReturn(true);

        // Act
        HealthCheckResponse response = healthCheck.call();

        // Assert
        assertEquals("rabbitmq", response.getName());
        assertTrue(response.getStatus().equals(HealthCheckResponse.Status.UP));
    }

    @Test
    void testHealthCheckWhenDisconnected() {
        // Arrange
        when(consumerService.isConnected()).thenReturn(false);

        // Act
        HealthCheckResponse response = healthCheck.call();

        // Assert
        assertEquals("rabbitmq", response.getName());
        assertTrue(response.getStatus().equals(HealthCheckResponse.Status.DOWN));
    }
}