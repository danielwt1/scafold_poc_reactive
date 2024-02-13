package co.com.bancolombia.model.productmodel.gateways.commands;

import co.com.bancolombia.model.productmodel.model.ProductModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductCommandRepository {

    Mono<Void> save(ProductModel productModel);
    Mono<Void> update(ProductModel productModel);
    Mono<Void> delete(Long id);

    Mono<ProductModel> findById(Long id);
    Mono<ProductModel> findByIdAndName(Long id,String name);
}
