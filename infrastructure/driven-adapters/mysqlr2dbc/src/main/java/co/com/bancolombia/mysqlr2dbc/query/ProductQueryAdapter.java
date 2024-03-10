package co.com.bancolombia.mysqlr2dbc.query;


import co.com.bancolombia.model.productmodel.gateways.query.ProductQueryRepository;
import co.com.bancolombia.model.productmodel.model.ProductModel;

import co.com.bancolombia.mysqlr2dbc.mapper.ProductQueryEntityMapper;
import co.com.bancolombia.mysqlr2dbc.repository.ProductRepositoryQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class ProductQueryAdapter implements ProductQueryRepository {
    private final ProductRepositoryQuery productoRepository;
    private final ProductQueryEntityMapper mapper;

    public ProductQueryAdapter(ProductRepositoryQuery productoRepository, ProductQueryEntityMapper mapper) {
        this.productoRepository = productoRepository;
        this.mapper = mapper;
    }

    @Override
    public Flux<ProductModel> getAll() {
        return this.productoRepository.findAll()
                .map(this.mapper::toModel);
    }




}
