package com.golamyusuf.demo.controllers;

import com.golamyusuf.demo.dtos.MessageRequest;
import com.golamyusuf.demo.entities.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @MessageMapping("/api/v1/message/send") // Maps messages sent to /app/send
    @SendTo("/topic/group") // Broadcasts messages to /topic/messages
    public Message sendMessage(Message message) {
        System.out.println(" 18 ChatController message "+message.toString());
        return message;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public MessageRequest addUser(@Payload MessageRequest message,
                           SimpMessageHeaderAccessor headerAccessor) {
        // Add user in web socket session
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
