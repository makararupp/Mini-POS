package makara.co.min_pos.models.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExchangeRateRequest {
    @NotNull(message = "Exchange rate must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Exchange rate must be greater than 0")
    private Double exchangeRate;
    @NotNull(message = "From currency must not be null")
    private String fromCurrency;
    @NotNull(message = "To currency must not be null")
    private String toCurrency;
}
