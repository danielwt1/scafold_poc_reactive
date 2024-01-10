package co.com.bancolombia.model.productmodel.model;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductModel {
    private Long id;
    private String nombre;
    private Integer precio  ;
}
