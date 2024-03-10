package co.com.bancolombia.model.productsyncdatabase.gateways;

import co.com.bancolombia.model.productsyncdatabase.ProductSyncDataBaseModel;
import reactor.core.publisher.Mono;

public interface ProductSyncDataBaseRepository {

    Mono<Void> save(ProductSyncDataBaseModel product);
    Mono<Void> update(ProductSyncDataBaseModel product);
}
