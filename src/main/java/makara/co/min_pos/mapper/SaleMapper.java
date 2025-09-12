package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.Sale;
import makara.co.min_pos.models.request.SaleRequest;
import makara.co.min_pos.models.response.SaleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    Sale toEntities(SaleRequest request);
    SaleResponse toDTO(Sale sale);

}
