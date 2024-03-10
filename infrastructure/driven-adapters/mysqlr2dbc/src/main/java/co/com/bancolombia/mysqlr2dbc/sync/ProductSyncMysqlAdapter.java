package co.com.bancolombia.mysqlr2dbc.sync;

import co.com.bancolombia.model.productsyncdatabase.ProductSyncDataBaseModel;
import co.com.bancolombia.model.productsyncdatabase.gateways.ProductSyncDataBaseRepository;
import co.com.bancolombia.mysqlr2dbc.mapper.ProductSyncMapper;
import co.com.bancolombia.mysqlr2dbc.repository.ProductRepositoryQuery;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class ProductSyncMysqlAdapter implements ProductSyncDataBaseRepository {
    private final ProductRepositoryQuery productRepositoryQuery;
    private final ProductSyncMapper productSyncMapper;

    public ProductSyncMysqlAdapter(ProductRepositoryQuery productRepositoryQuery, ProductSyncMapper productSyncMapper) {
        this.productRepositoryQuery = productRepositoryQuery;
        this.productSyncMapper = productSyncMapper;
    }

    @Override
    public Mono<Void> save(ProductSyncDataBaseModel product) {
        return this.productRepositoryQuery.insertProduct(product.getId(),product.getNombre(),product.getPrecio()).then();
    }



    @Override
    public Mono<Void> update(ProductSyncDataBaseModel product) {
        return this.productRepositoryQuery.save(this.productSyncMapper.toEntity(product)).then();
    }
}
