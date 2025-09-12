package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.ItemUnit;
import makara.co.min_pos.models.request.ItemUnitRequest;
import makara.co.min_pos.models.response.ItemUnitResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemUnitMapper {
    @Mapping(target = "parent.id",source = "parentId")
    ItemUnit toEntity(ItemUnitRequest request);
    @Mapping(target = "parentId",source = "parent.id")
    ItemUnitResponse toDTO(ItemUnit dto);
}
