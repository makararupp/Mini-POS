package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.PurchaseDetails;
import makara.co.min_pos.models.request.PurchaseDetailRequest;
import makara.co.min_pos.models.response.PurchaseDetailResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface PurchaseDetailService {
    PurchaseDetails createPurchaseDetail(Long  purchaseId, PurchaseDetailRequest details);
    PurchaseDetails getById(Long id);
    PurchaseDetails deleteById(Long id);
    PurchaseDetails update(Long id, PurchaseDetailRequest request);
    List<PurchaseDetailResponse> getAll();

    Page<PurchaseDetailResponse> pagination(Map<String, String> params);

}
