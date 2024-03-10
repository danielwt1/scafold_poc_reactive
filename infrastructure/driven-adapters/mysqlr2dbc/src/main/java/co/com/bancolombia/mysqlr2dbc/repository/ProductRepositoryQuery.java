package co.com.bancolombia.mysqlr2dbc.repository;

import co.com.bancolombia.mysqlr2dbc.entity.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepositoryQuery extends ReactiveCrudRepository<Producto, Long> {
    @Query("INSERT INTO producto (id, nombre, precio) VALUES (:id, :nombre, :precio)")
    Mono<Void> insertProduct(Long id, String nombre, Integer precio);
}
