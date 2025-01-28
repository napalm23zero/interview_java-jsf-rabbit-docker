package dev.hustletech.jsf.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dev.hustletech.jsf.config.RabbitMQConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RabbitMQServiceTest {

    @Mock
    private RabbitMQConfig config;

    @Mock
    private ConnectionFactory connectionFactory;

    @Mock
    private Connection connection;

    @Mock
    private Channel channel;

    @InjectMocks
    private RabbitMQService rabbitMQService;

    @BeforeEach
    void setUp() throws Exception {
        when(config.getFactory()).thenReturn(connectionFactory);
        when(connectionFactory.newConnection()).thenReturn(connection);
        when(connection.createChannel()).thenReturn(channel);
    }

    @Test
    void testSuccessfulPublish() throws Exception {
        // Arrange
        String message = "test message";

        // Act
        rabbitMQService.publishMessage(message);

        // Assert
        verify(channel).queueDeclare(eq(RabbitMQConfig.QUEUE_NAME), anyBoolean(), anyBoolean(), anyBoolean(), any());
        verify(channel).basicPublish(eq(""), eq(RabbitMQConfig.QUEUE_NAME), any(), eq(message.getBytes()));
    }

    @Test
    void testFailedConnection() throws Exception {
        // Arrange
        when(connectionFactory.newConnection()).thenThrow(new RuntimeException("Connection failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> rabbitMQService.publishMessage("test"));
    }

    @Test
    void testFailedChannelCreation() throws Exception {
        // Arrange
        when(connection.createChannel()).thenThrow(new RuntimeException("Channel creation failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> rabbitMQService.publishMessage("test"));
    }

    @Test
    void testFailedQueueDeclare() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Queue declare failed"))
                .when(channel).queueDeclare(any(), anyBoolean(), anyBoolean(), anyBoolean(), any());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> rabbitMQService.publishMessage("test"));
    }
}