package makara.co.min_pos.models.response;

import lombok.Data;
import makara.co.min_pos.models.enums.PaymentStatus;

import java.time.LocalDate;

@Data
public class SaleResponse {
    private Integer id;
    private LocalDate soldDate;
    private Double amount;
    private PaymentStatus paymentStatus;

}
