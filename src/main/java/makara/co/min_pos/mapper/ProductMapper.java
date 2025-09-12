package makara.co.min_pos.mapper;

import makara.co.min_pos.mapper.helper.MapperHelper;
import makara.co.min_pos.models.entities.ItemProduct;
import makara.co.min_pos.models.request.ItemProductRequest;
import makara.co.min_pos.models.response.ItemProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {MapperHelper.class})
public interface ProductMapper {
    @Mapping(target = "brand",source = "brandId",qualifiedByName = "getBrandById")
    ItemProduct toEntity(ItemProductRequest dto);

    @Mapping(target = "brandId",source = "brand.id")
    ItemProductResponse toDTO(ItemProduct list);

}
