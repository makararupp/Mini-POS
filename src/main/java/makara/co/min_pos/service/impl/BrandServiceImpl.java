package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.BrandMapper;
import makara.co.min_pos.models.entities.Brand;
import makara.co.min_pos.models.response.BrandResponse;
import makara.co.min_pos.repository.BrandRepository;
import makara.co.min_pos.service.BrandService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getById(Long id) {
        return brandRepository.findByIdAndIsDeletedFalse(id).orElseThrow(()->
                new ResourceNotFoundException("Brand",id));
    }

    @Override
    public Brand update(Long id, Brand newBrand) {
        Brand brand = getById(id);
        brand.setName(newBrand.getName());
        return brandRepository.save(brand);
    }

    @Override
    public Brand deleteById(Long id) {
         Brand byId = getById(id);
         byId.setIsDeleted(true);
         Brand save = brandRepository.save(byId);
        return save;
    }

    @Override
    public List<BrandResponse> listAllBrand() {
        return brandRepository.findByIsDeletedIsFalseOrderByIdDesc()
                .stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BrandResponse> getWithPagination(Map<String, String> params) {
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        /*Step 1 :Check page limit = 2*/
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
          pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        /*Step 2 : Check page number = 1*/
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }
        //TODO : Pageable check condition from repository
        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        Page<BrandResponse> page = brandRepository.findByIsDeletedIsFalseOrderByIdDesc(pageable)
                .map(brandMapper::toDto);
        return  page;
    }
}
