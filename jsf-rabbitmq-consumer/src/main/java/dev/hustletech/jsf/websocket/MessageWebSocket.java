package dev.hustletech.jsf.websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.java.Log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Log
@ApplicationScoped
@ServerEndpoint("/websocket/messages")
public class MessageWebSocket {

    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        log.info("🔌 WebSocket opened: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        log.info("🔌 WebSocket closed: " + session.getId());
    }

    public void broadcastMessage(String message) {
        sessions.forEach(session -> {
            try {
                session.getBasicRemote().sendText(message);
                log.info("📢 Message broadcasted: " + message);
            } catch (IOException e) {
                log.severe("💥 Failed to broadcast message: " + e.getMessage());
            }
        });
    }
}