package dev.hustletech.jsf.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageDTOTest {

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        MessageDTO messageDTO = new MessageDTO();

        // Assert
        assertNull(messageDTO.getContent());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String content = "Test content";

        // Act
        MessageDTO messageDTO = new MessageDTO(content);

        // Assert
        assertEquals(content, messageDTO.getContent());
    }

    @Test
    void testGetterAndSetter() {
        // Arrange
        MessageDTO messageDTO = new MessageDTO();
        String content = "Test content";

        // Act
        messageDTO.setContent(content);

        // Assert
        assertEquals(content, messageDTO.getContent());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        MessageDTO dto1 = new MessageDTO("content");
        MessageDTO dto2 = new MessageDTO("content");
        MessageDTO dto3 = new MessageDTO("different");

        // Assert
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testToString() {
        // Arrange
        MessageDTO messageDTO = new MessageDTO("test");

        // Act
        String toString = messageDTO.toString();

        // Assert
        assertTrue(toString.contains("content=test"));
    }
}