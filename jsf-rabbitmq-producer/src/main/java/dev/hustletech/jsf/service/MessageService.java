package dev.hustletech.jsf.service;

import dev.hustletech.jsf.model.Message;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import lombok.extern.java.Log;

@Log
@ApplicationScoped
public class MessageService {

    @Inject
    private RabbitMQService rabbitMQService;

    public void sendMessage(Message message) {
        if (message == null || message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty! 🚫");
        }

        String jsonMessage = JsonbBuilder.create().toJson(message);
        rabbitMQService.publishMessage(jsonMessage);

        log.info(() -> String.format("🚀 Message processed: %s", message.getContent()));
    }
}