package co.com.bancolombia.api.handler.command.mappers;

import co.com.bancolombia.api.handler.command.commands.ProductCommand;
import co.com.bancolombia.model.productmodel.model.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductCommandsMapper {
    ProductModel toModel(ProductCommand producto);
    ProductCommand toDTO(ProductModel producto);
}
