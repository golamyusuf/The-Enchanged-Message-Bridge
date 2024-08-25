package com.golamyusuf.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    Environment environment;

    private static final Logger LOGGER = LoggerFactory.getLogger((KafkaConsumer.class));

    @KafkaListener(topics = "enchantedTopic", groupId = "myGroup")
    public void consume(String message){
        LOGGER.info(String.format("Message recived -> %s", message));
    }
}
