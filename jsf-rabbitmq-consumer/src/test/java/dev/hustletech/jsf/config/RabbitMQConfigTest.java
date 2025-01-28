package dev.hustletech.jsf.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rabbitmq.client.ConnectionFactory;

class RabbitMQConfigTest {

    private RabbitMQConfig rabbitMQConfig;

    @BeforeEach
    void setUp() {
        rabbitMQConfig = new RabbitMQConfig();
    }

    @Test
    void testSuccessfulInitialization() {
        // Arrange
        assertNull(rabbitMQConfig.getFactory());

        // Act
        rabbitMQConfig.init();

        // Assert
        assertNotNull(rabbitMQConfig.getFactory());
    }

    @Test
    void testConnectionFactoryConfiguration() {
        // Arrange
        rabbitMQConfig.init();
        ConnectionFactory factory = rabbitMQConfig.getFactory();

        // Act & Assert
        assertEquals("rabbitmq", factory.getHost());
        assertEquals(5672, factory.getPort());
        assertEquals("guest", factory.getUsername());
        assertEquals("guest", factory.getPassword());
    }

    @Test
    void testQueueNameConstant() {
        // Arrange & Act & Assert
        assertEquals("jsfQueue", RabbitMQConfig.QUEUE_NAME);
    }

    @Test
    void testExceptionHandling() {
        // Arrange
        RabbitMQConfig configWithMockFactory = new RabbitMQConfig() {
            @Override
            public void init() {
                throw new RuntimeException("Test exception");
            }
        };

        // Act & Assert
        assertDoesNotThrow(() -> configWithMockFactory.init());
    }
}