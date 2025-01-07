package io.github.muriced.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public NewTopic deviceEventsTopic() {
        return TopicBuilder.name("device-events")
                .partitions(3)
                .replicas(1)
                .configs(Map.of(
                        "retention.ms", "604800000", // 7 dias
                        "cleanup.policy", "delete"))
                .build();
    }

    @Bean
    public NewTopic alertEventsTopic() {
        return TopicBuilder.name("alert-events")
                .partitions(3)
                .replicas(1)
                .configs(Map.of(
                        "retention.ms", "2592000000", // 30 dias
                        "cleanup.policy", "delete"))
                .build();
    }
}
