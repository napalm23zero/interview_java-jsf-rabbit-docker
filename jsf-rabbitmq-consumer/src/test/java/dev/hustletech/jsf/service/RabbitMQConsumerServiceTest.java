package dev.hustletech.jsf.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import dev.hustletech.jsf.config.RabbitMQConfig;
import dev.hustletech.jsf.websocket.MessageWebSocket;

@ExtendWith(MockitoExtension.class)
class RabbitMQConsumerServiceTest {

    @Mock
    private RabbitMQConfig config;

    @Mock
    private MessageWebSocket webSocket;

    @Mock
    private Connection connection;

    @Mock
    private Channel channel;

    @Mock
    private ConnectionFactory factory;

    @InjectMocks
    private RabbitMQConsumerService service;

    @BeforeEach
    void setUp() throws Exception {
        // Arrange
        when(config.getFactory()).thenReturn(factory);
        when(factory.newConnection()).thenReturn(connection);
        when(connection.createChannel()).thenReturn(channel);
    }

    @Test
    void testSuccessfulInitialization() throws Exception {
        // Arrange
        doNothing().when(channel).queueDeclare(anyString(), anyBoolean(), anyBoolean(), anyBoolean(), any());

        // Act
        service.init();

        // Assert
        assertTrue(service.isConnected());
        verify(config).getFactory();
        verify(factory).newConnection();
        verify(connection).createChannel();
        verify(channel).queueDeclare(eq(RabbitMQConfig.QUEUE_NAME), anyBoolean(), anyBoolean(), anyBoolean(), any());
    }

    @Test
    void testFailedConnection() throws Exception {
        // Arrange
        when(factory.newConnection()).thenThrow(new RuntimeException("Connection failed"));

        // Act
        service.init();

        // Assert
        assertFalse(service.isConnected());
        verify(config).getFactory();
        verify(factory).newConnection();
        verifyNoInteractions(channel);
    }

    @Test
    void testFailedChannelCreation() throws Exception {
        // Arrange
        when(connection.createChannel()).thenThrow(new RuntimeException("Channel creation failed"));

        // Act
        service.init();

        // Assert
        assertFalse(service.isConnected());
        verify(config).getFactory();
        verify(factory).newConnection();
        verify(connection).createChannel();
        verifyNoMoreInteractions(channel);
    }
}