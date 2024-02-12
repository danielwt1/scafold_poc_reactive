package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.productmodel.model.ProductModel;
import co.com.bancolombia.r2dbc.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductCommandEntityMapper {
    ProductModel toModel(Producto producto);
    Producto toEntity(ProductModel productModel);

}
