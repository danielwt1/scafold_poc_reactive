package co.com.bancolombia.r2dbc.repository;

import co.com.bancolombia.r2dbc.entity.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long>{
    @Query("SELECT * FROM producto p WHERE p.nombre =:name AND p.id <>:id")
    Mono<Producto> findByName(String name, Long id);
}
