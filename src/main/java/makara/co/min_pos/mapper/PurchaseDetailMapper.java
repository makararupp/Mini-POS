package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.PurchaseDetails;
import makara.co.min_pos.models.request.PurchaseDetailRequest;
import makara.co.min_pos.models.response.PurchaseDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PurchaseDetailMapper {

    PurchaseDetailMapper INSTANCE = Mappers.getMapper(PurchaseDetailMapper.class);

    @Mapping(source = "purchaseId", target = "purchase.id")    // Map purchaseId to Purchase's id
    @Mapping(source = "productId", target = "product.id")      // Map productId to ItemProduct's id
    @Mapping(source = "productName", target = "productName")
    PurchaseDetails toEntity(PurchaseDetailRequest purchaseDetailRequest);

    @Mapping(source = "purchase.id", target = "purchaseId")   // Map Purchase's id to purchaseId
    @Mapping(source = "product.id", target = "productId")      // Map ItemProduct's id to productId
    @Mapping(source = "productName", target = "productName")
    PurchaseDetailResponse toDTO(PurchaseDetails purchaseDetails);


}
