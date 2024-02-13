package co.com.bancolombia.events;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.productmodel.model.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


import java.util.logging.Level;


@Log
@RequiredArgsConstructor
@Component
public class ReactiveEventsGateway implements EventsGateway {
    public static final String SOME_EVENT_NAME = "some.event.name";
    private final Sinks.Many<DomainEvent> eventNotifications;



    @Override
    public Mono<Void> emit(Object event) {
        log.log(Level.INFO, "Sending domain event: {0}: {1}", new String[]{SOME_EVENT_NAME, event.toString()});
        //eventNotifications.tryEmitNext((DomainEvent) event);
        return Mono.fromCallable(() -> eventNotifications.tryEmitNext((DomainEvent) event))
                .then();
    }
    /**
     * Provide a flux with our notifications.
     *
     * @return a Flux
     */
    public Flux<DomainEvent> getNotifications() {
        return eventNotifications.asFlux();
    }



}
