package dev.hustletech.jsf.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;
    private LocalDateTime timestamp;

    public Message(String content) {
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
}