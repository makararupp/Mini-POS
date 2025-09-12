package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.CategoryMapper;
import makara.co.min_pos.models.entities.Category;
import makara.co.min_pos.models.response.CategoryResponse;
import makara.co.min_pos.repository.CategoryRepository;
import makara.co.min_pos.service.CategoryService;
import makara.co.min_pos.service.GeneralFileService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper itemCategoryMapper;
    private final GeneralFileService generalFileService;

    @Value("${file.server-path}")
    private String fileServerPath;


    @Override
    public Category save(Category category) {
        Category parentEntityCategory = new Category();
        parentEntityCategory.setCategoryName(category.getCategoryName());
        parentEntityCategory.setCategoryCode(category.getCategoryCode());
        parentEntityCategory.setOperator(category.getOperator());
        parentEntityCategory.setOperationValue(category.getOperationValue());
        parentEntityCategory.setCategoryPath(category.getCategoryPath());

        // Check condition if parent id is null on categories table..
        if(category.getParent() !=null && category.getParent().getId() !=null){
            parentEntityCategory.setParent(category.getParent());
        }
        return categoryRepository.save(parentEntityCategory);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository
                .findByIdIsAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("Category",id));
    }

    @Override
    public Category deleteById(Long id) {
        Category findId = getById(id);
        findId.setIsDeleted(true);
        Category save = categoryRepository.save(findId);
       return save;
    }

    @Override
    public List<CategoryResponse> listAllCategory() {

        return categoryRepository.findAllByParentIsNullAndIsDeletedFalseOrderByIdDesc()
                .stream()
                .map(itemCategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Category update(Long id, Category newCategory) {
        Category category = getById(id);
        category.setCategoryName(newCategory.getCategoryName());
        category.setCategoryCode(newCategory.getCategoryCode());
        category.setOperationValue(newCategory.getOperationValue());
        category.setOperator(newCategory.getOperator());
        if(category.getParent() !=null && category.getParent() !=null){
            Category parentCategory = getById(category.getParent().getId());
        }else {
            category.setParent(null);
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category saveImage(Long id, MultipartFile file) throws Exception {
        String fileName = generalFileService.generalFile(file.getOriginalFilename());
        String destinationPath = fileServerPath+fileName;
        file.transferTo(new File(destinationPath));

        Category saveImage = getById(id);
        saveImage.setCategoryPath(fileName);

        return categoryRepository.save(saveImage);
    }

    @Override
    public Page<CategoryResponse> getWithPagination(Map<String, String> params) {
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        Page<CategoryResponse> page = categoryRepository
                .findAllByParentIsNullAndIsDeletedFalseOrderByIdDesc(pageable)
                .map(itemCategoryMapper::toDto);

        return page;
    }

}
