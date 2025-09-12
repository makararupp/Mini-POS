package makara.co.min_pos.models.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductImportHistoriesResponse {
    private Long id;
    private Long productId;
    private Integer importUnit;
    private LocalDate dateImport;
    private BigDecimal pricePerUnit;
}
