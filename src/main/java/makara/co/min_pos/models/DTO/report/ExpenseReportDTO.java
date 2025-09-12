package makara.co.min_pos.models.DTO.report;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseReportDTO {
    private Long productId;
    private String productName;
    private Integer totalUnit;
    private BigDecimal totalAmount;
}
