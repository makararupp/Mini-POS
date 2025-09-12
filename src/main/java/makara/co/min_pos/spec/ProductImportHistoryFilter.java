package makara.co.min_pos.spec;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductImportHistoryFilter {
    @NotNull(message = "Start date must not be null")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;
    @NotNull(message = "End date must not be null")
    @PastOrPresent(message = "End date must be in the past or present")
    private LocalDate endDate;
}
