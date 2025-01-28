package dev.hustletech.jsf.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import dev.hustletech.jsf.model.Message;

@Named
@ApplicationScoped
public class MessageController {

    @Getter
    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(0, message);
        if (messages.size() > 100) {
            messages.remove(messages.size() - 1);
        }
    }
}