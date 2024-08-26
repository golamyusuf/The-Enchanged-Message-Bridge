package com.golamyusuf.demo.controllers;

import com.golamyusuf.demo.adapter.MessageAdapter;
import com.golamyusuf.demo.dtos.MessageDTO;
import com.golamyusuf.demo.dtos.MessageRequest;
import com.golamyusuf.demo.dtos.MessageResponse;
import com.golamyusuf.demo.entities.Message;
import com.golamyusuf.demo.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private KafkaTemplate<String, MessageRequest> kafkaTemplate;

    @Autowired
    Environment environment;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageAdapter messageAdapter;

    private final String TOPIC = "enchantedTopic"; // Replace with your Kafka topic name

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestPart(value="sender",  required = true)  String sender,
            @RequestPart(value="content", required = true) String content,
            @RequestPart(value = "file", required = false) MultipartFile file) {
       LOGGER.info("sender "+sender+" content "+content);
        try {
            MessageRequest messageRequest = new MessageRequest(sender, content, file);
            kafkaTemplate.send(TOPIC, messageRequest);

            Message message = messageAdapter.toEntity(messageRequest);
            messageService.saveMessage(message);

            LOGGER.info("Message saved to database for sender: {}", sender);
            return ResponseEntity.ok("Message sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send message: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<MessageResponse>> getAllMessages() throws IOException {
        try {
            List<MessageResponse> allMessages = messageService.getAllMessages();
            return ResponseEntity.ok(allMessages);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
