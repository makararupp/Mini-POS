package makara.co.min_pos.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import makara.co.min_pos.models.enums.PurchaseStatus;

import java.time.LocalDate;

@Data
public class PurchaseRequest {
    @NotNull(message = "supplier ID is required!")
    private Long supplierId;

    @NotNull(message = "purchase Date is required!")
    private LocalDate  purchaseDate;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be greater than zero")
    private Double totalAmount;

    @NotNull(message = "Status is required!")
    private PurchaseStatus status;

}
