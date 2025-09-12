package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.ExchangeRate;
import makara.co.min_pos.models.response.ExchangeRateResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ExchangeRateService {
    ExchangeRate create(ExchangeRate exchangeRate);
    ExchangeRate getById(Long id);
    ExchangeRate delete(Long id);
    ExchangeRate update(Long id, ExchangeRate newExchangeRate);
    List<ExchangeRateResponse> listAll();
    Page<ExchangeRateResponse> getWithPagination(Map<String ,String > params);

}
