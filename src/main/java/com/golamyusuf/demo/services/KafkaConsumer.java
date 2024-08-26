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

   /* @KafkaListener(topics = "enchantedTopic", groupId = "myGroup")
    public void consume(String message){
        LOGGER.info(String.format("Message recived -> %s", message));
        template.convertAndSend("/topic/group", message);
    }*/

    @KafkaListener(topics = "enchantedTopic", groupId = "myGroup")
    public void listen(MessageRequest message) {
        // Convert byte array back to MultipartFile if needed
        MultipartFile file = message.toMultipartFile();

        // Process the message
        System.out.println("Sender: " + message.getSender());
        System.out.println("Content: " + message.getContent());
        System.out.println("File name: " + file.getOriginalFilename());
        System.out.println("File name: " + file.getSize());
        messagingTemplate.convertAndSend("/topic/group", message);
    }
}
