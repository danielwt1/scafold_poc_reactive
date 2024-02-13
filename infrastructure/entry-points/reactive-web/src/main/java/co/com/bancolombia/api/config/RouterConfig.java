package co.com.bancolombia.api.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {
    @Bean
    public WebProperties.Resources getResources(){
        return new WebProperties.Resources();
    }
}
