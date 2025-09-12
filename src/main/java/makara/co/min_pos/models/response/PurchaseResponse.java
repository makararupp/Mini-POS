package makara.co.min_pos.models.response;

import lombok.Data;
import makara.co.min_pos.models.enums.PurchaseStatus;

import java.time.LocalDate;

@Data
public class PurchaseResponse {
    private Long id;
    private LocalDate purchaseDate;
    private String supplierEngName;
    private Double totalAmount;
    private PurchaseStatus status;

}
