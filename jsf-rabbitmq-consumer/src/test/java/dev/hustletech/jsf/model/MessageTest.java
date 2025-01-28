package dev.hustletech.jsf.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testConstructorWithContent() {
        // Arrange
        String content = "Test message";

        // Act
        Message message = new Message(content);

        // Assert
        assertEquals(content, message.getContent());
        assertNotNull(message.getTimestamp());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String content = "Test message";
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        Message message = new Message(content, timestamp);

        // Assert
        assertEquals(content, message.getContent());
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        Message message = new Message();

        // Assert
        assertNull(message.getContent());
        assertNull(message.getTimestamp());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Message message = new Message();
        String content = "Test content";
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        message.setContent(content);
        message.setTimestamp(timestamp);

        // Assert
        assertEquals(content, message.getContent());
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        Message message1 = new Message("content", timestamp);
        Message message2 = new Message("content", timestamp);

        // Act & Assert
        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());
    }
}