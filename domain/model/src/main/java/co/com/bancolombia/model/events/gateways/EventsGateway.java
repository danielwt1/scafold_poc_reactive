package co.com.bancolombia.model.events.gateways;

import co.com.bancolombia.model.productmodel.model.event.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventsGateway {
    Mono<Void> emit(Object event);

    Flux<DomainEvent> getNotifications();


}
