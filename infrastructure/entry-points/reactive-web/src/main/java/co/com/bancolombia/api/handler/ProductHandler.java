package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.dto.ProductDTO;
import co.com.bancolombia.api.mappers.ProductDtoMapper;
import co.com.bancolombia.usecase.product.ProductUseCase;
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
public class ProductHandler {
    private  final ProductUseCase useCase;
    private final ProductDtoMapper mapper;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<ProductDTO> response = this.useCase.getAll()
                .map(this.mapper::toDTO);
        //Mono<>
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response,ProductDTO.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<ProductDTO> body = request.bodyToMono(ProductDTO.class);
        return body.flatMap(element -> ServerResponse
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.useCase.save(mapper.toModel(element)),String.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<ProductDTO> body = request.bodyToMono(ProductDTO.class);
        return body.flatMap(element -> ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.useCase.update(id,mapper.toModel(element)),String.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<String> response = this.useCase.delete(id);
        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response,String.class);
    }



}
