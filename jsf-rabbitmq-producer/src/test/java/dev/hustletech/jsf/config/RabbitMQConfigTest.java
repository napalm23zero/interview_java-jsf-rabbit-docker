package dev.hustletech.jsf.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RabbitMQConfigTest {

    private RabbitMQConfig config;

    @BeforeEach
    void setUp() {
        // Clear any environment variables that might interfere
        System.clearProperty("RABBITMQ_HOST");
        System.clearProperty("RABBITMQ_PORT");
        System.clearProperty("RABBITMQ_USERNAME");
        System.clearProperty("RABBITMQ_PASSWORD");
        System.clearProperty("RABBITMQ_QUEUE");

        config = new RabbitMQConfig();
    }

    @Test
    void testDefaultConfiguration() {
        // Act
        config.init();

        // Assert
        assertEquals("localhost", config.getFactory().getHost());
        assertEquals(5672, config.getFactory().getPort());
        assertEquals("guest", config.getFactory().getUsername());
        assertEquals("guest", config.getFactory().getPassword());
        assertEquals("jsfQueue", RabbitMQConfig.QUEUE_NAME);
    }

    @Test
    void testCustomConfiguration() {
        // Arrange
        System.setProperty("RABBITMQ_HOST", "custom-host");
        System.setProperty("RABBITMQ_PORT", "5673");
        System.setProperty("RABBITMQ_USERNAME", "user");
        System.setProperty("RABBITMQ_PASSWORD", "pass");
        System.setProperty("RABBITMQ_QUEUE", "customQueue");

        // Act
        config.init();

        // Assert
        assertEquals("custom-host", config.getFactory().getHost());
        assertEquals(5673, config.getFactory().getPort());
        assertEquals("user", config.getFactory().getUsername());
        assertEquals("pass", config.getFactory().getPassword());
        assertEquals("customQueue", RabbitMQConfig.QUEUE_NAME);
    }

    @Test
    void testInvalidPortConfiguration() {
        // Arrange
        System.setProperty("RABBITMQ_PORT", "invalid");

        // Act & Assert
        assertThrows(NumberFormatException.class, () -> config.init());
    }
}