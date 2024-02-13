package co.com.bancolombia.model.productmodel.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class DomainEventData {
    private UUID id;
    private String type;
    private LocalDate occurredOn;
    private Object attributes;
}
