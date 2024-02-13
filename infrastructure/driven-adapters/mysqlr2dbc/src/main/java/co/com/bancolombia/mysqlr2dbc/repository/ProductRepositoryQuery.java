package co.com.bancolombia.mysqlr2dbc.repository;

import co.com.bancolombia.mysqlr2dbc.entity.Producto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryQuery extends ReactiveCrudRepository<Producto, Long> {
}
