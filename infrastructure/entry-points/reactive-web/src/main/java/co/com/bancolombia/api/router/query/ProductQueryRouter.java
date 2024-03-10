package co.com.bancolombia.api.router.query;

import co.com.bancolombia.api.handler.query.ProductQuerysHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductQueryRouter {
    private static final String PRODUCT_PATH = "product/v1/query";



    @Bean
    RouterFunction<ServerResponse> queryRouter(ProductQuerysHandler handler) {
        return RouterFunctions.route()
                .GET(PRODUCT_PATH, handler::getAll)

                .build();
    }
}
