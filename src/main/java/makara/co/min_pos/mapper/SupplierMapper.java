package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.Supplier;
import makara.co.min_pos.models.request.SupplierRequest;
import makara.co.min_pos.models.response.SupplierResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toEntity(SupplierRequest request);
    SupplierResponse toDTO(Supplier supplier);

}
