package co.com.bancolombia.api.router.command;

import co.com.bancolombia.api.handler.command.ProductCommandHandler;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductCommandRouter {
    private static final String PRODUCT_PATH = "product/v1/commands";



    @Bean
    RouterFunction<ServerResponse> commandrouter(ProductCommandHandler handler) {
        return RouterFunctions.route()
                .POST(PRODUCT_PATH, handler::save)
                .PUT(PRODUCT_PATH + "/{id}", handler::update)
                .DELETE(PRODUCT_PATH + "/{id}", handler::delete)
                .build();
    }
}
