package com.eon.in.products;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

    @Bean
    NewTopic createTopics() {
        LOGGER.info("Creating Kafka topic: products-created-events-topic");
        return TopicBuilder.name("products-created-events-topic")
                .partitions(3)
                .replicas(1)
                .configs(Map.of("min.insync.replicas","1"))
                .build();
    }

}
