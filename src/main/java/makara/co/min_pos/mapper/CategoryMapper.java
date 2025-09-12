package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.Category;
import makara.co.min_pos.models.request.CategoryRequest;
import makara.co.min_pos.models.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "parent.id",source = "parentId")
    Category toEntity(CategoryRequest request);
    @Mapping(target = "parentId",source = "parent.id")
    CategoryResponse toDto(Category itemCategory);
    // I will to update to INSTANCE

}
