package co.com.bancolombia.listener;

import co.com.bancolombia.model.productmodel.model.event.DomainEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Connection;
import lombok.extern.java.Log;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.rabbitmq.BindingSpecification;
import reactor.rabbitmq.ExchangeSpecification;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Log
public class RabbitMQEventListener {

    private final Sinks.Many<DomainEvent> eventBus;
    private final Mono<Connection> connectionMono;
    private final Sender sender;

    public RabbitMQEventListener(Sinks.Many<DomainEvent> eventBus, Mono<Connection> connectionMono, Sender sender) {
        this.eventBus = eventBus;
        this.connectionMono = connectionMono;
        this.sender = sender;
    }

    @PostConstruct
    public void init() {
        this.subscribeToEvents();
    }

    @PreDestroy
    public void close() throws Exception {
        connectionMono.block().close();
    }

    public void subscribeToEvents() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        eventBus.asFlux()
                //.filter(event -> event.getData().getType().equals("domain.event.product.created"))
                .map(ele -> {
                    String message = null;
                    try {
                        message = mapper.writeValueAsString(ele);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return new OutboundMessage("CUSTOM_DIRECT", ele.getData().getType(), message.getBytes());
                })
                .flatMap(el -> this.sendtoRabbit(el, el.getRoutingKey()))
                .subscribe(event -> {
                    System.out.println("Evento Procesado y enviado a cola rabbit");
                });
    }

    // crea cola y declara que tipo de exchange sera Direct, topic o fanout etc
    private Mono<Void> sendtoRabbit(OutboundMessage message, String queue) {
        return sender.declareQueue(QueueSpecification.queue(queue))
                .thenMany(sender.declareExchange(ExchangeSpecification.exchange("CUSTOM_DIRECT").type(ExchangeTypes.DIRECT).durable(true)))
                .thenMany(sender.bindQueue(BindingSpecification.binding("CUSTOM_DIRECT",queue, queue)))
                .thenMany(sender.sendWithPublishConfirms(Mono.just(message)))

                .doOnError(e -> log.info("Error")).then();
        //.subscribe( m ->log.info("Mensaje enviado"));
    }

    /* example: cuando quiero digamos enviar todos los eventos de un tipo a una sola cola*/
    /*
    private Mono<Void> sendtoRabbit(OutboundMessage message) {
        String queue = "com.co.domain.event.product";
        String routingKey = "domain.event.product.*";
        return sender.declareQueue(QueueSpecification.queue(queue))
                .thenMany(sender.declareExchange(ExchangeSpecification.exchange("CUSTOM_TOPIC").type(ExchangeTypes.TOPIC).durable(true)))
                .thenMany(sender.bindQueue(BindingSpecification.binding("CUSTOM_TOPIC", routingKey, queue)))
                .thenMany(sender.sendWithPublishConfirms(Mono.just(message)))
                .doOnError(e -> log.info("Error")).then();
    }
     */

}