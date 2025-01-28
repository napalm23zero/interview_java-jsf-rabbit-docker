package dev.hustletech.jsf.rest;

import dev.hustletech.jsf.dto.MessageDTO;
import dev.hustletech.jsf.model.Message;
import dev.hustletech.jsf.service.MessageService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageResourceTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageResource messageResource;

    @Test
    void testSuccessfulMessageSend() {
        // Arrange
        MessageDTO dto = new MessageDTO("test message");

        // Act
        Response response = messageResource.sendMessage(dto);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("success", entity.get("status"));
        verify(messageService).sendMessage(any(Message.class));
    }

    @Test
    void testFailedMessageSend() {
        // Arrange
        MessageDTO dto = new MessageDTO("test message");
        String errorMessage = "Test error";
        doThrow(new RuntimeException(errorMessage))
                .when(messageService).sendMessage(any());

        // Act
        Response response = messageResource.sendMessage(dto);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        Map<String, String> entity = (Map<String, String>) response.getEntity();
        assertEquals("error", entity.get("status"));
        assertEquals(errorMessage, entity.get("message"));
    }
}