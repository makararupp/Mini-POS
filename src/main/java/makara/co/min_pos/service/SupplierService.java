package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.Supplier;
import makara.co.min_pos.models.response.SupplierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SupplierService {
    Supplier create(Supplier supplier);
    Supplier getById(Long id);
    Supplier updateSupplier(Long id, Supplier newSupplier);
    Supplier deleteId(Long id);
    List<SupplierResponse> listAll();
    Page<SupplierResponse> getAllPagination(Map<String, String> params);
}
