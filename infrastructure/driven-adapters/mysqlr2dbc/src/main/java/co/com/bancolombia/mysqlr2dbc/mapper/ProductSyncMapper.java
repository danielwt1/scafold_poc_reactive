package co.com.bancolombia.mysqlr2dbc.mapper;

import co.com.bancolombia.model.productsyncdatabase.ProductSyncDataBaseModel;
import co.com.bancolombia.mysqlr2dbc.entity.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductSyncMapper {
    ProductSyncDataBaseModel toModel(Producto producto);
    Producto toEntity(ProductSyncDataBaseModel productModel);
}
