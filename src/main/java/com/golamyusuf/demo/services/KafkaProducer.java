package com.golamyusuf.demo.services;

import com.golamyusuf.demo.dtos.MessageRequest;
import com.golamyusuf.demo.entities.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    Environment environment;

    private KafkaTemplate<String, MessageRequest> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, MessageRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String sender, String content, MultipartFile file) throws IOException {
        MessageRequest message = new MessageRequest(sender, content, file);
        kafkaTemplate.send(topic, message);
    }
}
