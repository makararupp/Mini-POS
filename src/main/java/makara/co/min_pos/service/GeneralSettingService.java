package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.GeneralSetting;
import makara.co.min_pos.models.response.ExchangeRateResponse;
import makara.co.min_pos.models.response.GeneralSettingResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;
import java.util.Map;

public interface GeneralSettingService {
    GeneralSetting create(GeneralSetting setting);
    GeneralSetting getById(Long id);
    GeneralSetting update(Long id, GeneralSetting newSetting);
    GeneralSetting deleteById(Long id);
    List<GeneralSettingResponse> listAll();
    Page<GeneralSettingResponse> getWithPagination(Map<String,String> params);
}
