package co.com.bancolombia.model.productmodel.gateways;

import co.com.bancolombia.model.productmodel.model.ProductModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductModelRepository {

    Flux<ProductModel> getAll();
    Mono<Void> save(ProductModel productModel);
    Mono<Void> update(ProductModel productModel);
    Mono<Void> delete(Long id);
    Mono<ProductModel> findByIdAndName(Long id,String name);
    Mono<ProductModel> findById(Long id);
}
