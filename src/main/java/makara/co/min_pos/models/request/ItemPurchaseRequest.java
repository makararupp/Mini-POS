package makara.co.min_pos.models.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemPurchaseRequest {

    @NotNull(message = "Item Product ID is required")
    private Long itemProductId;
    @NotNull(message = "Unit name is required")
    @Size(max = 10, message = "Unit name must not exceed 10 characters")
    private String unitName;

    @NotNull(message = "Conversion factor is required")
    @Positive(message = "Conversion factor must be positive")
    private Double conversionFactor;

    @NotNull(message = "Cost per unit is required")
    @Positive(message = "Cost per unit must be positive")
    private Double costPerUnit;

    @NotNull(message = "Purchase date is required")
    private LocalDate purchaseDate;


}
