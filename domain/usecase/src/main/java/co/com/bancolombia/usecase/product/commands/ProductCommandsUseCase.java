package co.com.bancolombia.usecase.product.commands;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.productmodel.gateways.commands.ProductCommandRepository;
import co.com.bancolombia.model.productmodel.model.ProductModel;
import co.com.bancolombia.model.productmodel.model.event.DomainEvent;
import co.com.bancolombia.model.productmodel.model.event.DomainEventData;
import co.com.bancolombia.model.productmodel.model.event.MetadataModel;
import co.com.bancolombia.usecase.product.exceptions.ProductException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductCommandsUseCase {
    // driven port
    private final ProductCommandRepository productRepository;
    private final EventsGateway eventsGateway;
    public Mono<String> save(ProductModel productModel) {
        return this.productRepository.save(productModel)
                .then(eventsGateway.emit(new DomainEvent(new DomainEventData(UUID.randomUUID(), "domain.event.product.created", LocalDate.now(), productModel), new MetadataModel("host", "prueba"))))
                .then(Mono.just("Se guardo con exito"));
    }
    public Mono<String> update(Long id, ProductModel model) {
        return this.productRepository
                .findById(id)
                .hasElement()
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new RuntimeException("Error No hay con ese Id"));
                    }
                    return this.productRepository.findByIdAndName(id, model.getNombre()).hasElement();
                })
                .flatMap(existByName -> {
                    if (existByName) {
                        return Mono.error(new RuntimeException("Existe ya por ese nombre"));
                    } else {
                        model.setId(id);
                        return this.productRepository.update(model);
                    }
                })
                .onErrorMap(e -> new ProductException(String.format("Error al guardar la causa fue : %s", e.getMessage())))
                //no bloquea puede ser para quizas logear algun error o algo asi
                .doOnError(err -> System.out.println(String.format("Oucrrio el error: %s", err)))

                .map(el -> "Se actualizo correctamente");
    }
    public Mono<String> delete(Long id) {
        return this.productRepository.findById(id)
                .hasElement().flatMap(exist -> Boolean.TRUE.equals(exist) ? this.delete(id) : Mono.error(new RuntimeException("No existe")));
    }
}
