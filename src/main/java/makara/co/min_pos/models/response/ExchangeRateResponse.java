package makara.co.min_pos.models.response;

import lombok.Data;

@Data
public class ExchangeRateResponse {
    private Integer id;
    private Double ExchangeRate;
    private String fromCurrency;
    private String toCurrency;
}
