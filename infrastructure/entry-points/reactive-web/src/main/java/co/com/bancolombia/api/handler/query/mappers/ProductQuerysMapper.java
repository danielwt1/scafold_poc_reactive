package co.com.bancolombia.api.handler.query.mappers;

import co.com.bancolombia.api.handler.query.querys.ProductQuery;
import co.com.bancolombia.model.productmodel.model.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductQuerysMapper {
    ProductModel toModel(ProductQuery producto);
    ProductQuery toDTO(ProductModel producto);
}
