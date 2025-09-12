package makara.co.min_pos.models.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductSaleRequest {

    @NotNull(message = "Product ID cannot be null")
    private Long productId;
    @NotNull(message = "Sale ID cannot be null")
    private Long saleId;
    @NotNull(message = "Quantity cannot be null")
    private Integer qty;
    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    private Double unitPrice;

    @DecimalMin(value = "0", message = "Total Price cannot be negative")
    private Double totalPrice;

    @DecimalMin(value = "0", message = "Discount cannot be negative")
    private Double discount;

}
