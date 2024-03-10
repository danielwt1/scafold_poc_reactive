package co.com.bancolombia.model.productmodel.gateways.query;


import co.com.bancolombia.model.productmodel.model.ProductModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductQueryRepository {

    Flux<ProductModel> getAll();



}
