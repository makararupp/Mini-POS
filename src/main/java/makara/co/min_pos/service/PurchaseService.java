package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.Purchase;
import makara.co.min_pos.models.request.PurchaseRequest;
import makara.co.min_pos.models.response.PurchaseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PurchaseService {
    Purchase create(PurchaseRequest request);
    Purchase getById(Long id);
    Purchase deleteById(Long id);
    Purchase update(Long id, Purchase newPurchase);
    List<PurchaseResponse> getAllPurchase();
    Page<PurchaseResponse> findWithPagination(Map<String, String> params);

}
