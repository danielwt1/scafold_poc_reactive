package co.com.bancolombia.api.handler.query.querys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductQuery {
    private Long id;
    private String nombre;
    private Integer precio ;
}
