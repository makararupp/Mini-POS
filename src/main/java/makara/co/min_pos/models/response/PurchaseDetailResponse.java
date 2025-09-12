package makara.co.min_pos.models.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PurchaseDetailResponse {
    private Integer id;
    private Integer purchaseId;
    private Integer productId;
    private String productName;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer unit;
    private LocalDate createdDate;

}
