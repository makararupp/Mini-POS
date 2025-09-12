package makara.co.min_pos.models.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ItemPurchaseResponse {
    private Integer id; // ID of the purchase
    private Integer itemProductId; // ID of the item product
    private String unitName; // Name of the unit
    private Double conversionFactor; // Conversion factor
    private Double costPerUnit; // Cost per unit
    private LocalDate purchaseDate; // Date of purchase
}
