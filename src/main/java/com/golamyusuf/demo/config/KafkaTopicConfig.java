package com.golamyusuf.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Autowired
    Environment environment;

    @Bean
    public NewTopic enchantedTopic(){
        return TopicBuilder.name(environment.getProperty("kafka.config.enchanted.topic"))
                .build();
    }
}
