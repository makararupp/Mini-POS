package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.ItemUnit;
import makara.co.min_pos.models.response.ItemUnitResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ItemUnitService {
    ItemUnit create(ItemUnit itemUnit);
    ItemUnit getById(Long id);
    ItemUnit deleteById(Long id);
    ItemUnit updateUnit(Long id, ItemUnit newItemUnit);
    List<ItemUnitResponse> getAllUnit();
    Page<ItemUnitResponse> findWithPagination(Map<String, String> params);
}
