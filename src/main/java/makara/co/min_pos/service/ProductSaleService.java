package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.ProductSale;
import makara.co.min_pos.models.request.ProductSaleRequest;
import makara.co.min_pos.models.response.ProductSaleResponse;


public interface ProductSaleService {
    ProductSale create(ProductSaleRequest saleRequest);
    void verifySaleExistsAndIsPending(Long saleId);
    ProductSaleResponse incrementQty(Long productSaleId);
    ProductSaleResponse decrementQty(Long productSaleId);
    ProductSaleResponse getById(Long saleId, Long productSaleId);

    ProductSale updateDiscountRate (Long productSaleId, Double discount);

    ProductSale deleteProductSale(Long productSaleId);


}
