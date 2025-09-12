package makara.co.min_pos.models.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PurchaseDetailRequest {
    @NotNull(message = "Purchase ID must not be null")
    @Positive(message = "Purchase ID must be a positive number")
    private Integer purchaseId;

    @NotNull(message = "Product ID must not be null")
    private  Integer productId;

    @NotNull(message = "Product name in required")
    private String productName;

    @NotNull(message = "Unit price must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;

    @NotNull(message = "Unit must not be null")
    @Positive(message = "Unit must be a positive number")
    private Integer unit;

    @NotNull(message = "Total must not be null")
    @Positive(message = "Total price must be a positive number")
    private BigDecimal totalPrice;

    @NotNull(message = "Created date must not be null")
    @PastOrPresent(message = "Created date must be in the past or present")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

}
