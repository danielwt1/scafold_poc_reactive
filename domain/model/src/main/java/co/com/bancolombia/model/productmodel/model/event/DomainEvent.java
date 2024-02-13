package co.com.bancolombia.model.productmodel.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DomainEvent {
    private DomainEventData data;
    private MetadataModel metadata;
}
