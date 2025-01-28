package dev.hustletech.jsf.controller;

import dev.hustletech.jsf.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MessageControllerTest {

    private MessageController controller;

    @BeforeEach
    void setUp() {
        controller = new MessageController();
    }

    @Test
    void testInitialState() {
        // Arrange & Act
        var messages = controller.getMessages();

        // Assert
        assertNotNull(messages);
        assertTrue(messages.isEmpty());
    }

    @Test
    void testAddMessage() {
        // Arrange
        Message message = new Message("test");

        // Act
        controller.addMessage(message);

        // Assert
        assertEquals(1, controller.getMessages().size());
        assertEquals(message, controller.getMessages().get(0));
    }

    @Test
    void testMessageOrder() {
        // Arrange
        Message first = new Message("first");
        Message second = new Message("second");

        // Act
        controller.addMessage(first);
        controller.addMessage(second);

        // Assert
        assertEquals(second, controller.getMessages().get(0));
        assertEquals(first, controller.getMessages().get(1));
    }

    @Test
    void testListSizeLimit() {
        // Arrange
        for (int i = 0; i < 101; i++) {
            controller.addMessage(new Message("Message " + i));
        }

        // Act & Assert
        assertEquals(100, controller.getMessages().size());
        assertEquals("Message 100", controller.getMessages().get(0).getContent());
        assertEquals("Message 1", controller.getMessages().get(99).getContent());
    }
}