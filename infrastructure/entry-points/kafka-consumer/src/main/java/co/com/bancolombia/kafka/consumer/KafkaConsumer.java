package co.com.bancolombia.kafka.consumer;

import co.com.bancolombia.model.productsyncdatabase.ProductSyncDataBaseModel;
import co.com.bancolombia.usecase.sincdatabase.ProductSyncUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Log4j2
@RequiredArgsConstructor
@Component
public class KafkaConsumer {
    private final ReactiveKafkaConsumerTemplate<String, Object> kafka;
    private final ProductSyncUseCase productSyncUseCase;

    @EventListener(ApplicationStartedEvent.class)
    public Flux<Void> listenMessages() {
        ObjectMapper objectMapper = new ObjectMapper();
        return kafka
                .receiveAutoAck()
                .publishOn(Schedulers.newBoundedElastic(Schedulers.DEFAULT_BOUNDED_ELASTIC_SIZE, Schedulers.DEFAULT_BOUNDED_ELASTIC_QUEUESIZE, "kafka"))
                .flatMap(kafkaRecord -> {
                    log.info("Record received {}", kafkaRecord.value());
                    try {
                        // Convert the record value to a JsonNode
                        JsonNode jsonNode = objectMapper.readTree((String) kafkaRecord.value());

                        // Extract the 'after' field, which contains the product data
                        JsonNode afterNode = jsonNode.get("payload").get("after");

                        // Convert the 'after' field to a ProductSyncDataBaseModel
                        ProductSyncDataBaseModel product = objectMapper.treeToValue(afterNode, ProductSyncDataBaseModel.class);

                        return this.productSyncUseCase.saveProduct(product);
                    } catch (JsonProcessingException e) {
                        log.error("Error processing kafka record", e);
                        return Mono.empty();
                    }})
                .doOnError(error -> log.error("Error processing kafka record", error))
                .retry()
                .repeat();
    }
}
