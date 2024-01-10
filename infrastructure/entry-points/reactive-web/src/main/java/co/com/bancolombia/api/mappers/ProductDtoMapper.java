package co.com.bancolombia.api.mappers;

import co.com.bancolombia.api.dto.ProductDTO;
import co.com.bancolombia.model.productmodel.model.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductDtoMapper {
    ProductModel toModel(ProductDTO producto);
    ProductDTO toDTO(ProductModel producto);
}
