package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.ImportProductHistory;
import makara.co.min_pos.models.entities.ItemProduct;
import makara.co.min_pos.models.request.ProductImportHistoriesRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImportProductHistoriesMapper {
    ImportProductHistoriesMapper INSTANCE = Mappers.getMapper(ImportProductHistoriesMapper.class);
    ProductImportHistoriesRequest toDTO(ImportProductHistory entity);
    @Mapping(target = "product", source = "product")
    @Mapping(target = "id", ignore = true)
    ImportProductHistory toEntity(ProductImportHistoriesRequest request, ItemProduct product);
}
