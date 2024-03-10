package co.com.bancolombia.api.handler.query;

import co.com.bancolombia.api.handler.query.mappers.ProductQuerysMapper;
import co.com.bancolombia.api.handler.query.querys.ProductQuery;
import co.com.bancolombia.usecase.product.querys.ProductQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductQuerysHandler {
    private  final ProductQueryUseCase useCase;
    private final ProductQuerysMapper mapper;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<ProductQuery> response = this.useCase.getAll()
                .map(this.mapper::toDTO);
        //Mono<>
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response,ProductQuery.class);
    }


}
