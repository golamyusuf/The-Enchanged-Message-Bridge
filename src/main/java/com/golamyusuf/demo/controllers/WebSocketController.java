package com.golamyusuf.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.HashSet;
import java.util.Set;

@Controller
public class WebSocketController {

    private Set<String> connectedUsers = new HashSet<>();


    @MessageMapping("/connect")
    @SendTo("/topic/users")
    public Set<String> connect(String username) {
        connectedUsers.add(username);
        return connectedUsers;
    }

    @MessageMapping("/disconnect")
    @SendTo("/topic/users")
    public Set<String> disconnect(String username) {
        connectedUsers.remove(username);
        return connectedUsers;
    }

    @ControllerAdvice
    public class WebSocketEventListener {

        @EventListener
        public void handleWebSocketConnectListener(SessionConnectEvent event) {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
            String username = headerAccessor.getFirstNativeHeader("X-User-Name");

            connectedUsers.add(username);
        }
    }
}

