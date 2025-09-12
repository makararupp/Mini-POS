package makara.co.min_pos.spec;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseDetailFilter {
    private LocalDate startDate;
    private LocalDate endDate;
}
