package co.com.bancolombia.config.events;

import co.com.bancolombia.model.productmodel.model.event.DomainEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.publisher.Sinks;

@Configuration
public class EventConfiguration {


    @Bean
    public Sinks.Many<DomainEvent> eventNotifications() {
        return Sinks.many().replay().latest();
    }


}
