package makara.co.min_pos.models.DTO;

import lombok.Data;

@Data
public class ProductSaleDTO {
    private Long productId;
    private Long saleId;
    private Double price;

}
