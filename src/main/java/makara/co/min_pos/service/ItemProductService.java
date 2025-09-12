package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.ItemProduct;
import makara.co.min_pos.models.request.ProductImportHistoriesRequest;
import makara.co.min_pos.models.response.ItemProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ItemProductService {
    ItemProduct create(ItemProduct itemProduct);
    ItemProduct getById(Long id);
    ItemProduct deleteBydId(Long id);
    ItemProduct update(Long id, ItemProduct newProduct);
    List<ItemProductResponse> listProduct();
    ItemProduct uploadImage(Long id, MultipartFile file)throws Exception;
    Page<ItemProductResponse> getWithPagination(Map<String,String> params);

    void ImportProduct(ProductImportHistoriesRequest dto);
}
