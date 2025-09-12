package makara.co.min_pos.models.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import makara.co.min_pos.models.enums.PaymentStatus;

import java.time.LocalDate;

@Data
public class SaleRequest {
    @NotNull(message = "sold date time is required")
    private LocalDate soldDate;
    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than 0")
    private Double amount;
    @NotNull(message = "payment status is required")
    private PaymentStatus paymentStatus;

}
