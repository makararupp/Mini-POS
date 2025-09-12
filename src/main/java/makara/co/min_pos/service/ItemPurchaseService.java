package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.PurchaseItemUnit;
import makara.co.min_pos.models.response.ItemPurchaseResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ItemPurchaseService {
    PurchaseItemUnit save(PurchaseItemUnit purchaseItem);
    PurchaseItemUnit getById(Long id);
    PurchaseItemUnit deleteById(Long id);
    PurchaseItemUnit updateById(Long id, PurchaseItemUnit unit);
    List<ItemPurchaseResponse> getAllPurchase();
    Page<ItemPurchaseResponse> getWithPagination(Map<String, String> params);

}
