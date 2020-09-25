package config;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import serializer.KafkaValueSerializer;

@Configuration
@EnableKafka
public class KafkaConfig {

    private String groupId;
    @Value("${kafka.server}")
    private String kafkaServer;

    @Bean
    public String groupId(@Value("${kafka.hub.group.id}") String groupId) {
        this.groupId = groupId;
        return groupId;
    }

    @Bean
    public ProducerFactory<String, Object> producerBatchFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaValueSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean
    public KafkaTemplate<String, Object> kafkaBatchTemplate() {
        return new KafkaTemplate<>(producerBatchFactory());
    }
}
