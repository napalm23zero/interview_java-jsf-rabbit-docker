package dev.hustletech.jsf.rest;

import java.util.Map;

import dev.hustletech.jsf.dto.MessageDTO;
import dev.hustletech.jsf.model.Message;
import dev.hustletech.jsf.service.MessageService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

    @Inject
    private MessageService messageService;

    @POST
    public Response sendMessage(MessageDTO messageDTO) {
        try {
            Message message = new Message(messageDTO.getContent());
            messageService.sendMessage(message);
            return Response.ok()
                    .entity(Map.of("status", "success",
                            "message", "Message sent successfully! 🚀"))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("status", "error",
                            "message", e.getMessage()))
                    .build();
        }
    }
}