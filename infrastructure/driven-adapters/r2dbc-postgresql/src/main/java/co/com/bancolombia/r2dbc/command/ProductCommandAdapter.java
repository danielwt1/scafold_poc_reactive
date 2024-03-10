package co.com.bancolombia.r2dbc.command;


import co.com.bancolombia.model.productmodel.gateways.commands.ProductCommandRepository;
import co.com.bancolombia.model.productmodel.model.ProductModel;

import co.com.bancolombia.r2dbc.mapper.ProductCommandEntityMapper;
import co.com.bancolombia.r2dbc.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProductCommandAdapter implements ProductCommandRepository {
    private final ProductoRepository productoRepository;
    private final ProductCommandEntityMapper mapper;

    public ProductCommandAdapter(ProductoRepository productoRepository, ProductCommandEntityMapper mapper) {
        this.productoRepository = productoRepository;
        this.mapper = mapper;
    }
    @Override
    public Mono<Void> save(ProductModel productModel) {

        return this.productoRepository
                .save(this.mapper.toEntity(productModel))
                .then();
    }

    @Override
    public Mono<Void> update(ProductModel productModel) {
        return this.productoRepository
                .save(this.mapper.toEntity(productModel))
                .then();
    }

    @Override
    public Mono<Void> delete(Long id) {
        return this.productoRepository.deleteById(id);
    }
    @Override
    public Mono<ProductModel> findByIdAndName(Long id, String name) {
        return this.productoRepository
                .findByName(name,id)
                .map(this.mapper::toModel);
    }

    @Override
    public Mono<ProductModel> findById(Long id) {
        return this.productoRepository
                .findById(id)
                .map(this.mapper::toModel);
    }

}
