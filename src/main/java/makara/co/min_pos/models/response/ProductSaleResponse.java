package makara.co.min_pos.models.response;

import lombok.Data;

@Data
public class ProductSaleResponse {
    private Long id;
    private Long saleId;
    private Integer  qty;
    private Double unitPrice;
    private Double totalPrice;
    private Double discount;
}
