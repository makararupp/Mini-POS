package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.PurchaseDetailMapper;
import makara.co.min_pos.models.entities.ItemProduct;
import makara.co.min_pos.models.entities.Purchase;
import makara.co.min_pos.models.entities.PurchaseDetails;
import makara.co.min_pos.models.request.PurchaseDetailRequest;
import makara.co.min_pos.models.response.PurchaseDetailResponse;
import makara.co.min_pos.repository.ProductRepository;
import makara.co.min_pos.repository.PurchaseDetailRepository;
import makara.co.min_pos.repository.PurchaseRepository;
import makara.co.min_pos.service.PurchaseDetailService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseDetailServiceImpl implements PurchaseDetailService {
    private final PurchaseDetailRepository detailRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final PurchaseDetailMapper detailMapper;

    @Override
    public  PurchaseDetails createPurchaseDetail(Long purchaseId, PurchaseDetailRequest details) {

            Purchase purchase =  purchaseRepository.findByIdAndIsDeletedFalse(purchaseId)
                    .orElseThrow(()-> new ResourceNotFoundException("Purchase",purchaseId));

            PurchaseDetails purchaseDetails = new PurchaseDetails();
            purchaseDetails.setPurchase(purchase);
            //purchaseDetails.setProductName(details.getProductName());
            purchaseDetails.setUnitPrice(details.getUnitPrice());
            purchaseDetails.setUnit(details.getUnit());
            purchaseDetails.setTotalPrice(details.getTotalPrice());
            purchaseDetails.setCreatedDate(details.getCreatedDate());

            if(details.getProductId() != null){
                Long productId = Long.valueOf(details.getProductId());
                ItemProduct product = productRepository.findByIdAndIsDeletedFalse(productId)
                        .orElseThrow(()-> new ResourceNotFoundException("Product",productId));
                purchaseDetails.setProduct(product);
                purchaseDetails.setProductName(product.getProductName());
            }
            return detailRepository.save(purchaseDetails);

    }
    @Override
    public PurchaseDetails getById(Long id) {
        return detailRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("PurchaseDetail",id));
    }

    @Override
    public PurchaseDetails deleteById(Long id) {
        PurchaseDetails details = getById(id);
        details.setIsDeleted(true);
        PurchaseDetails delete = detailRepository.save(details);
        return delete;
    }

    @Override
    public PurchaseDetails update(Long id, PurchaseDetailRequest request) {
        PurchaseDetails details = detailRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotFoundException("PurchaseDetail",id));

        details.setProductName(request.getProductName());
        details.setUnitPrice(request.getUnitPrice());
        details.setUnit(request.getUnit());
        details.setCreatedDate(request.getCreatedDate());

        if(request.getProductId() != null){
            Long productId = Long.valueOf(request.getProductId());
            ItemProduct product = productRepository.findByIdAndIsDeletedFalse(productId)
                    .orElseThrow(()-> new ResourceNotFoundException("Product",productId));
            details.setProduct(product);
            details.setProductName(product.getProductName());
        }

        //calculate and update derived field and, if any
        if (request.getUnitPrice() != null && request.getUnit() != null) {
            // Multiply BigDecimal and Integer
            BigDecimal totalPrice = request.getUnitPrice().multiply(BigDecimal.valueOf(request.getUnit()));
            details.setTotalPrice(totalPrice);
        }
        return detailRepository.save(details);
    }

    @Override
    public List<PurchaseDetailResponse> getAll() {
        return detailRepository.findByProductIsDeletedFalseOrderByIdDesc()
                .stream()
                .map(detailMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PurchaseDetailResponse> pagination(Map<String, String> params) {
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }
        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        Page<PurchaseDetailResponse> page = detailRepository
                .findByProductIsDeletedFalseOrderByIdDesc(pageable)
                .map(detailMapper::toDTO);
        return page;
    }
}
