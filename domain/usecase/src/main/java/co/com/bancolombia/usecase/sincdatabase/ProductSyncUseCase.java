package co.com.bancolombia.usecase.sincdatabase;

import co.com.bancolombia.model.productsyncdatabase.ProductSyncDataBaseModel;
import co.com.bancolombia.model.productsyncdatabase.gateways.ProductSyncDataBaseRepository;
import reactor.core.publisher.Mono;

public class ProductSyncUseCase {
    private final ProductSyncDataBaseRepository productSyncDataBaseRepository;

    public ProductSyncUseCase(ProductSyncDataBaseRepository productSyncDataBaseRepository) {
        this.productSyncDataBaseRepository = productSyncDataBaseRepository;
    }

    public Mono<Void> saveProduct(ProductSyncDataBaseModel product) {
        return this.productSyncDataBaseRepository.save(product);
    }

    public Mono<Void> updateProduct(ProductSyncDataBaseModel product) {
        return this.productSyncDataBaseRepository.update(product);
    }
    public Mono<Void> deleteProduct(Long id) {
        return this.productSyncDataBaseRepository.delete(id);
    }

}
