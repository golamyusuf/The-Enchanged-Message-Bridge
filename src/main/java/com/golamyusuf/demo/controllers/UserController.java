package com.golamyusuf.demo.controllers;

import com.golamyusuf.demo.dtos.UserRequest;
import com.golamyusuf.demo.dtos.UserResponse;
import com.golamyusuf.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private  SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        LOGGER.info("UserResponse "+userResponse);
        return userResponse != null ? ResponseEntity.ok(userResponse) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        LOGGER.info("users "+users);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.updateUser(id, userRequest);
        return userResponse != null ? ResponseEntity.ok(userResponse) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        LOGGER.info("id "+id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest request) {
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/connected")
    public void sendUsers() {
        Set<String> users = userService.getActiveUsers();
        messagingTemplate.convertAndSend("/topic/users", users);
    }

}

