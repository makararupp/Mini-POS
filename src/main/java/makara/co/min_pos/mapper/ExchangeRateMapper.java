package makara.co.min_pos.mapper;

import makara.co.min_pos.models.entities.ExchangeRate;
import makara.co.min_pos.models.request.ExchangeRateRequest;
import makara.co.min_pos.models.response.ExchangeRateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExchangeRateMapper {
    ExchangeRate toEntity(ExchangeRateRequest request);
    ExchangeRateResponse toDTO(ExchangeRate exchangeRate);

}
