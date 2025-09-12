package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.Purchase;
import makara.co.min_pos.models.request.PurchaseRequest;
import makara.co.min_pos.models.response.PurchaseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    @Mapping(source = "supplierId", target = "supplier.id")
    Purchase toEntity(PurchaseRequest request);
    @Mapping(source = "supplier.supplierEngName", target = "supplierEngName")
    PurchaseResponse toDTO(Purchase dto);

}
