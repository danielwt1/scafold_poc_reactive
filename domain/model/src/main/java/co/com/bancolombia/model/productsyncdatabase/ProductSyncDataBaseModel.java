package co.com.bancolombia.model.productsyncdatabase;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductSyncDataBaseModel {
    private Long id;
    private String nombre;
    private Integer precio ;
}
