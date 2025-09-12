package makara.co.min_pos.models.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductImportHistoriesRequest {

    @NotNull(message = "Product id can't be null")
    private Long productId;

    @Min(value = 1, message = "import unit must be greater than 0")
    private Integer importUnit;

    @NotNull(message = "Import date can't be null")
    private LocalDate dateImport;

    @DecimalMin(value = "0.000001", message = "Price must be greater than 0")
    private BigDecimal pricePerUnit;

}
