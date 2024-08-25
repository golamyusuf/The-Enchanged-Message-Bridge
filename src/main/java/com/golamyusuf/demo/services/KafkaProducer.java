package com.golamyusuf.demo.services;

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

    private KafkaTemplate<String, Message> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

   /* public void sendMessage(String message) {
        LOGGER.info(String.format("Topic name is  %s", environment.getProperty("kafka.config.enchanted.topic")));
        LOGGER.info(String.format("Message sent %s", message));
        kafkaTemplate.send(environment.getProperty("kafka.config.enchanted.topic"), message);
    }*/


    public void sendMessage(String topic, String sender, String content, MultipartFile file) throws IOException {
        Message message = new Message(sender, content, file);
        kafkaTemplate.send(topic, message);
    }
}
