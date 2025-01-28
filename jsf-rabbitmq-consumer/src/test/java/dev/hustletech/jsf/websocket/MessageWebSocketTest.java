package dev.hustletech.jsf.websocket;

import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageWebSocketTest {

    @InjectMocks
    private MessageWebSocket webSocket;

    @Mock
    private Session session;

    @Mock
    private RemoteEndpoint.Basic basic;

    @BeforeEach
    void setUp() {
        when(session.getId()).thenReturn("test-session-id");
        when(session.getBasicRemote()).thenReturn(basic);
    }

    @Test
    void testOnOpen() {
        // Arrange & Act
        webSocket.onOpen(session);

        // Assert
        verify(session).getId();
    }

    @Test
    void testOnClose() {
        // Arrange
        webSocket.onOpen(session);

        // Act
        webSocket.onClose(session);

        // Assert
        verify(session, times(2)).getId();
    }

    @Test
    void testBroadcastMessage() throws IOException {
        // Arrange
        webSocket.onOpen(session);
        String message = "Test message";

        // Act
        webSocket.broadcastMessage(message);

        // Assert
        verify(basic).sendText(message);
    }

    @Test
    void testBroadcastMessageWithError() throws IOException {
        // Arrange
        webSocket.onOpen(session);
        String message = "Test message";
        doThrow(new IOException("Test exception")).when(basic).sendText(any());

        // Act
        webSocket.broadcastMessage(message);

        // Assert
        verify(basic).sendText(message);
    }
}