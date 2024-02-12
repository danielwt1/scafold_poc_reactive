package co.com.bancolombia.api.router;

import co.com.bancolombia.api.handler.ProductHandler;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductQueryRouter {
    private static final String PRODUCT_PATH = "product";

    @Bean
    public WebProperties.Resources getResources(){
        return new WebProperties.Resources();
    }

    @Bean
    RouterFunction<ServerResponse> router(ProductHandler handler) {
        return RouterFunctions.route()
                .GET(PRODUCT_PATH, handler::getAll)
                .POST(PRODUCT_PATH, handler::save)
                .PUT(PRODUCT_PATH + "/{id}", handler::update)
                .DELETE(PRODUCT_PATH + "/{id}", handler::delete)
                .build();
    }
}
