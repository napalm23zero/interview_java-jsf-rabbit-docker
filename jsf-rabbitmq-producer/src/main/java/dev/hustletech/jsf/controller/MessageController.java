package dev.hustletech.jsf.controller;

import dev.hustletech.jsf.model.Message;
import dev.hustletech.jsf.service.MessageService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Named
@ViewScoped
public class MessageController implements Serializable {

    @Inject
    private MessageService messageService;

    @Getter
    @Setter
    private Message message = new Message();

    public String sendMessage() {
        try {
            messageService.sendMessage(message);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success! 🎉", "Message sent to queue"));
            message = new Message(); // Reset form
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error! 💥", e.getMessage()));
            return null;
        }
    }
}