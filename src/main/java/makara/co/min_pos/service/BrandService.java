package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.Brand;
import makara.co.min_pos.models.response.BrandResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface BrandService {
    Brand create(Brand brand);
    Brand getById(Long id);
    Brand update(Long id, Brand newBrand);
    Brand deleteById(Long id);
    List<BrandResponse> listAllBrand();
    Page<BrandResponse> getWithPagination(Map<String, String> params);

}
