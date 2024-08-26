package com.golamyusuf.demo.config;

import com.golamyusuf.demo.controllers.MessageController;
import com.golamyusuf.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);
    @Autowired
    private UserService userService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = headerAccessor.getFirstNativeHeader("X-User-Name");
        if (username != null) {
            LOGGER.info("Connected user: " + username);
            userService.addUser(username);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = headerAccessor.getFirstNativeHeader("X-User-Name");
        if (username != null) {
            LOGGER.info("Disconnected user: " + username);
            userService.removeUser(username);
        }
    }
}

