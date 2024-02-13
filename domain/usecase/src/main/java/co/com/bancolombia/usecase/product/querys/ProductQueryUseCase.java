package co.com.bancolombia.usecase.product.querys;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import co.com.bancolombia.model.productmodel.gateways.query.ProductQueryRepository;
import co.com.bancolombia.model.productmodel.model.ProductModel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class ProductQueryUseCase {
    // driven port
    private final ProductQueryRepository productRepository;
    private final EventsGateway eventsGateway;
    public Flux<ProductModel> getAll() {
        return this.productRepository.getAll();
    }

}
