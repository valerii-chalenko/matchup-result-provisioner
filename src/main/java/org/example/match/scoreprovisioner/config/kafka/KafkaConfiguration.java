package org.example.match.scoreprovisioner.config.kafka;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<UUID, String> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.RETRIES_CONFIG, 5);
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 15000);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }


    @Bean
    public KafkaTemplate<UUID, String> kafkaTemplate(
            ProducerFactory<UUID, String> producerFactory
    ) {
        KafkaTemplate<UUID, String> template = new KafkaTemplate<>(producerFactory);
        template.setProducerListener(new ProducerListener<>() {
            @Override
            public void onError(
                    ProducerRecord<UUID, String> producerRecord,
                    RecordMetadata recordMetadata,
                    Exception exception
            ) {
                val eventId = producerRecord.key();
                log.warn("[KAFKA, {}] Error occurred while sending event: {}", eventId, exception.toString());
            }

            @Override
            public void onSuccess(
                    ProducerRecord<UUID, String> producerRecord,
                    RecordMetadata recordMetadata
            ) {
                val key = producerRecord.key();
                val score = producerRecord.value();
                log.info("[KAFKA, {}] Successfully sent kafka event: {}", key, score);
            }
        });
        return template;
    }
}
