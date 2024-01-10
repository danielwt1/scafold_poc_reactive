package co.com.bancolombia.api.exception;


import co.com.bancolombia.usecase.product.exceptions.ProductException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new HashMap<>();
        Throwable throwable = super.getError(request);
        errorAttributes.put("date", LocalDate.now());
        errorAttributes.put("path", request.uri().toString());

        errorAttributes.put("message", throwable.getMessage());

        if (throwable instanceof ProductException) {

            errorAttributes.put("status", HttpStatus.BAD_REQUEST);
        }

        return errorAttributes;
    }
}
