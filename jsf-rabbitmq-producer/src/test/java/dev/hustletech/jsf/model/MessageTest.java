package dev.hustletech.jsf.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        Message message = new Message();

        // Assert
        assertNull(message.getContent());
        assertNull(message.getTimestamp());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String content = "test";
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        Message message = new Message(content, timestamp);

        // Assert
        assertEquals(content, message.getContent());
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    void testContentOnlyConstructor() {
        // Arrange
        String content = "test";

        // Act
        Message message = new Message(content);

        // Assert
        assertEquals(content, message.getContent());
        assertNotNull(message.getTimestamp());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        Message message1 = new Message("test", timestamp);
        Message message2 = new Message("test", timestamp);

        // Assert
        assertEquals(message1, message2);
        assertEquals(message1.hashCode(), message2.hashCode());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Message message = new Message();
        String content = "test";
        LocalDateTime timestamp = LocalDateTime.now();

        // Act
        message.setContent(content);
        message.setTimestamp(timestamp);

        // Assert
        assertEquals(content, message.getContent());
        assertEquals(timestamp, message.getTimestamp());
    }
}