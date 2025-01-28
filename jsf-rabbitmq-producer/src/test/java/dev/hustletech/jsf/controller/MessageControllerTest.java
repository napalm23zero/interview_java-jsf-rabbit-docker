package dev.hustletech.jsf.controller;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.hustletech.jsf.model.Message;
import dev.hustletech.jsf.service.MessageService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @Mock
    private FacesContext facesContext;

    @InjectMocks
    private MessageController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitialState() {
        // Assert
        assertNotNull(controller.getMessage());
    }

    @Test
    void testSuccessfulMessageSend() {
        // Arrange
        Message message = new Message();
        message.setContent("Test message");
        controller.setMessage(message);

        // Act
        String result = controller.sendMessage();

        // Assert
        verify(messageService).sendMessage(message);
        verify(facesContext).addMessage(eq(null), any(FacesMessage.class));
        assertNotEquals(message, controller.getMessage());
        assertNull(result);
    }

    @Test
    void testFailedMessageSend() {
        // Arrange
        doThrow(new RuntimeException("Test error"))
                .when(messageService).sendMessage(any());

        // Act
        String result = controller.sendMessage();

        // Assert
        verify(messageService).sendMessage(any());
        verify(facesContext).addMessage(eq(null), any(FacesMessage.class));
        assertNull(result);
    }
}