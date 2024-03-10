package co.com.bancolombia.kafka.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    /*
    @Bean
public ReceiverOptions<String, Object> kafkaReceiverOptions(@Value(value = "${adapters.kafka.consumer.topic}") String topic, KafkaProperties kafkaProperties) {
    Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());

    // Configura un nuevo grupo de consumidores para el nuevo consumidor
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "new-consumer-group");

    ReceiverOptions<String, Object> basicReceiverOptions = ReceiverOptions.create(props);
    return basicReceiverOptions.subscription(Collections.singletonList(topic));
}
     */

    @Bean
    public ReceiverOptions<String, Object> kafkaReceiverOptions(@Value(value = "${adapters.kafka.consumer.topic}") String topic, KafkaProperties kafkaProperties) {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties(null));

        // Configura un nuevo grupo de consumidores para el nuevo consumidor
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "sinc-product-consumer-group");

        ReceiverOptions<String, Object> basicReceiverOptions = ReceiverOptions.create(props);
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, Object> reactiveKafkaConsumerTemplate(ReceiverOptions<String, Object> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, Object>(kafkaReceiverOptions);
    }
}
