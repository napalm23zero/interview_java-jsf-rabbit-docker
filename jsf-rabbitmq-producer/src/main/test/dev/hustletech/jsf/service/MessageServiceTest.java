package dev.hustletech.jsf.service;

import dev.hustletech.jsf.model.Message;
import jakarta.json.bind.JsonbBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private RabbitMQService rabbitMQService;

    @InjectMocks
    private MessageService messageService;

    @Test
    void testNullMessage() {
        assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(null));
    }

    @Test
    void testEmptyContent() {
        Message message = new Message("");
        assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(message));
    }

    @Test
    void testBlankContent() {
        Message message = new Message("   ");
        assertThrows(IllegalArgumentException.class, () -> messageService.sendMessage(message));
    }

    @Test
    void testSuccessfulMessageSend() {
        // Arrange
        Message message = new Message("test content");
        String expectedJson = JsonbBuilder.create().toJson(message);

        // Act
        messageService.sendMessage(message);

        // Assert
        verify(rabbitMQService).publishMessage(expectedJson);
    }
}