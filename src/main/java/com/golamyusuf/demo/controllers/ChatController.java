package com.golamyusuf.demo.controllers;

import com.golamyusuf.demo.dtos.MessageRequest;
import com.golamyusuf.demo.entities.Message;
import com.golamyusuf.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@RestController
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/api/v1/message/send") // Maps messages sent to /app/send
    @SendTo("/topic/group") // Broadcasts messages to /topic/messages
    public Message sendMessage(Message message) {
        LOGGER.info("message "+message.toString());
        return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public void addUser(@Payload MessageRequest message,
                           SimpMessageHeaderAccessor headerAccessor) {
        String username = message.getSender();
        // Add user in web socket session
        headerAccessor.getSessionAttributes().put("username", username);
        userService.addUser(username);
        LOGGER.info("username "+username);
        messagingTemplate.convertAndSend("/topic/users", userService.getActiveUsers());
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        String username = (String) event.getSessionId();
        userService.removeUser(username);

        // Broadcast the updated user list
        messagingTemplate.convertAndSend("/topic/users", userService.getActiveUsers());
    }
}
