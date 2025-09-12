package makara.co.min_pos.service;

import makara.co.min_pos.models.entities.Category;
import makara.co.min_pos.models.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Category save(Category category);
    Category getById(Long id);
    Category deleteById(Long id);
    List<CategoryResponse> listAllCategory();
    Category update(Long id, Category newCategory);
    Category saveImage(Long id, MultipartFile file) throws Exception;
    Page<CategoryResponse> getWithPagination(Map<String,String> params);

}
