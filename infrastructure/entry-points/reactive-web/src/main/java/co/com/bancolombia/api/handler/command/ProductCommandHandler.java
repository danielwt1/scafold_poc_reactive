package co.com.bancolombia.api.handler.command;

import co.com.bancolombia.api.handler.command.commands.ProductCommand;
import co.com.bancolombia.api.handler.command.mappers.ProductCommandsMapper;
import co.com.bancolombia.usecase.product.commands.ProductCommandsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductCommandHandler {
    private  final ProductCommandsUseCase useCase;
    private final ProductCommandsMapper mapper;
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<ProductCommand> body = request.bodyToMono(ProductCommand.class);
        return body.flatMap(element -> ServerResponse
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.useCase.save(mapper.toModel(element)),String.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        Mono<ProductCommand> body = request.bodyToMono(ProductCommand.class);
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
