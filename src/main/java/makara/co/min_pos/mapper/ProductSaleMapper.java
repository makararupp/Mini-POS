package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.ProductSale;
import makara.co.min_pos.models.request.ProductSaleRequest;
import makara.co.min_pos.models.response.ProductSaleResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface ProductSaleMapper {

    ProductSale toEntity(ProductSaleRequest productSaleRequest);
    ProductSaleResponse toResponse(ProductSale productSale);

    @AfterMapping
    default void calculateTotalPrice(@MappingTarget ProductSaleResponse response,
                                     ProductSale productSale) {
        if (productSale.getDiscount() != null) {
            response.setDiscount(productSale.getDiscount());
        } else {
            response.setDiscount(0.0); // Default to 0 if null
        }
        if (productSale.getSale() != null) {
            response.setSaleId(productSale.getSale().getId());  // Map the saleId if not found.
        }
        if (productSale.getQty() != null && productSale.getUnitPrice() != null) {
            response.setTotalPrice(productSale.getQty() * productSale.getUnitPrice());
        }
    }
}
