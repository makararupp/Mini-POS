package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.Brand;
import makara.co.min_pos.models.request.BrandRequest;
import makara.co.min_pos.models.response.BrandResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toEntity (BrandRequest request);
    BrandResponse toDto(Brand entity);

}
