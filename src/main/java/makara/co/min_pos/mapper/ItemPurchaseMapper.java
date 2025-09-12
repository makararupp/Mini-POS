package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.PurchaseItemUnit;
import makara.co.min_pos.models.request.ItemPurchaseRequest;
import makara.co.min_pos.models.response.ItemPurchaseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemPurchaseMapper {
    ItemPurchaseMapper INSTANCE = Mappers.getMapper(ItemPurchaseMapper.class);
    @Mapping(target = "itemProduct.id", source = "itemProductId")
    PurchaseItemUnit toEntity(ItemPurchaseRequest request);

    @Mapping(target = "itemProductId", source = "itemProduct.id")
    ItemPurchaseResponse toDTO(PurchaseItemUnit purchaseItemUnit);

}
