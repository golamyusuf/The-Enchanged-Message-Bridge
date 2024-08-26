package com.golamyusuf.demo.services;

import com.golamyusuf.demo.dtos.MessageRequest;
import com.golamyusuf.demo.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class KafkaConsumer {

    @Autowired
    Environment environment;

    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger((KafkaConsumer.class));

    public KafkaConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "enchantedTopic", groupId = "myGroup")
    public void listen(MessageRequest message) {
        // Convert byte array back to MultipartFile if needed
        MultipartFile file = message.toMultipartFile();

        // Process the message
        LOGGER.info("Sender: " + message.getSender());
        LOGGER.info("Content: " + message.getContent());
        LOGGER.info("File name: " + file.getOriginalFilename());
        LOGGER.info("File name: " + file.getSize());
        messagingTemplate.convertAndSend("/topic/group", message);
    }
}
