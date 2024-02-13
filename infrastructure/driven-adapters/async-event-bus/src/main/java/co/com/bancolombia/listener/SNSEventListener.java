package co.com.bancolombia.listener;

import co.com.bancolombia.model.productmodel.model.event.DomainEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

import javax.annotation.PostConstruct;

@Component
public class SNSEventListener {

    private final Sinks.Many<DomainEvent> eventBus;

    public SNSEventListener(Sinks.Many<DomainEvent> eventBus) {
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void init() {
        this.subscribeToEvents();
    }

    public void subscribeToEvents() {
        eventBus.asFlux()
                .filter(event -> event.getData().getType().equals("domain.event.product.c" +
                        "ated"))
                .subscribe(event -> {
                    // TODO implementar manejo en cola sqs
                    System.out.println("Evento Procesado y enviado a cola SQS");
                });
    }
}