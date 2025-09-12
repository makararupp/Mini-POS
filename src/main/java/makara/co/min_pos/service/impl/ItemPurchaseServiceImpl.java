package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.ItemPurchaseMapper;
import makara.co.min_pos.models.entities.PurchaseItemUnit;
import makara.co.min_pos.models.response.ItemPurchaseResponse;
import makara.co.min_pos.repository.ProductRepository;
import makara.co.min_pos.repository.ItemPurchaseRepository;
import makara.co.min_pos.service.ItemPurchaseService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemPurchaseServiceImpl implements ItemPurchaseService {
    private final ItemPurchaseRepository purchaseRepository;
    private final ItemPurchaseMapper purchaseMapper;
    private final ProductRepository productRepository;

    @Override
    public PurchaseItemUnit save(PurchaseItemUnit purchaseItem) {
//        Long itemProductId = purchaseItem.getItemProduct().getId();
//
//        if(!productRepository.existsById(itemProductId)){
//            throw  new ResourceNotFoundException("Product",itemProductId);
//        }
//        return purchaseRepository.save(purchaseItem);

        verifyItemProductExists(purchaseItem.getItemProduct().getId());
        return purchaseRepository.save(purchaseItem);

    }
    private void verifyItemProductExists(Long itemProductId){
        productRepository.findById(itemProductId)
                .orElseThrow(()->new ResourceNotFoundException("Product",itemProductId));
    }
    @Override
    public PurchaseItemUnit getById(Long id) {
        return purchaseRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("Purchase",id));
    }

    @Override
    public PurchaseItemUnit deleteById(Long id) {
       PurchaseItemUnit itemUnitId = getById(id);
       itemUnitId.setIsDeleted(true);
        return purchaseRepository.save(itemUnitId);
    }

    @Override
    public PurchaseItemUnit updateById(Long id, PurchaseItemUnit unit) {
          PurchaseItemUnit unitId = getById(id);
            unitId.setUnitName(unit.getUnitName());
            unitId.setConversionFactor(unit.getConversionFactor());
            unitId.setCostPerUnit(unit.getCostPerUnit());
            unitId.setPurchaseDate(unit.getPurchaseDate());

        return purchaseRepository.save(unitId);
    }

    @Override
    public List<ItemPurchaseResponse> getAllPurchase() {
        return purchaseRepository.findByIsDeletedIsFalseOrderByIdDesc()
                .stream()
                .map(purchaseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ItemPurchaseResponse> getWithPagination(Map<String, String> params) {
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }
        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        Page<ItemPurchaseResponse> page = purchaseRepository
                .findByIsDeletedIsFalseOrderByIdDesc(pageable)
                .map(purchaseMapper::toDTO);

        return page;
    }
}
