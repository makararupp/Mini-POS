package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.PurchaseMapper;
import makara.co.min_pos.models.entities.Purchase;
import makara.co.min_pos.models.entities.Supplier;
import makara.co.min_pos.models.enums.PurchaseStatus;
import makara.co.min_pos.models.request.PurchaseRequest;
import makara.co.min_pos.models.response.PurchaseResponse;
import makara.co.min_pos.repository.PurchaseRepository;
import makara.co.min_pos.repository.SupplierRepository;
import makara.co.min_pos.service.PurchaseService;
import makara.co.min_pos.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseMapper purchaseMapper;

    @Override
    public Purchase create(PurchaseRequest request) {

        Supplier supplier = supplierRepository.findByIdAndIsDeletedFalse(request.getSupplierId())
                .orElseThrow(()-> new ResourceNotFoundException("Supplier Id not found", request.getSupplierId()));

        // Step 2: Fetch All Purchases for the Supplier
        List<Purchase> purchases = purchaseRepository.findAllBySupplierIdAndIsDeletedFalse(request.getSupplierId());

        // Step 3: Calculate Existing Total Using Lambda
        double existingTotal = purchases.stream()
                .mapToDouble(Purchase::getTotalAmount)
                .sum();

        // Step 4: Calculate New Total
        double newTotalAmount = existingTotal + request.getTotalAmount();


        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setPurchaseDate(request.getPurchaseDate());
        purchase.setTotalAmount(newTotalAmount);
        purchase.setStatus(PurchaseStatus.valueOf(String.valueOf(request.getStatus())));

        return purchaseRepository.save(purchase);

    }

    @Override
    public Purchase getById(Long id) {
        return purchaseRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("Purchase",id));
    }

    @Override
    public Purchase deleteById(Long id) {
        Purchase purchase = getById(id);
        purchase.setIsDeleted(true);
        Purchase save = purchaseRepository.save(purchase);
        return save;
    }

    @Override
    public Purchase update(Long id, Purchase newPurchase) {
        Purchase exitingPurchase = getById(id);

        Supplier exitingSupplier =  supplierRepository.findByIdAndIsDeletedFalse(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Supplier",id));

        if(exitingPurchase.getSupplier() !=null && exitingPurchase.getSupplier() !=null){
            Purchase parentPurchase1 = getById(exitingPurchase.getSupplier().getId());
        }else {
            exitingPurchase.setSupplier(null);
        }

        exitingPurchase.setPurchaseDate(newPurchase.getPurchaseDate());
        exitingPurchase.setSupplier(newPurchase.getSupplier());
        exitingPurchase.setTotalAmount(newPurchase.getTotalAmount());
        exitingPurchase.setStatus(newPurchase.getStatus());

        return purchaseRepository.save(exitingPurchase);
    }

    @Override
    public List<PurchaseResponse> getAllPurchase() {
        return purchaseRepository
                .findBySupplierIsDeletedFalseOrderByIdDesc()
                .stream()
                .map(purchaseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PurchaseResponse> findWithPagination(Map<String, String> params) {
        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if(params.containsKey(PageUtil.PAGE_LIMIT)){
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }
        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if(params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }
        Pageable pageable = PageUtil.getPageable(pageNumber,pageLimit);
        Page<PurchaseResponse> page = purchaseRepository
                .findBySupplierIsDeletedFalseOrderByIdDesc(pageable)
                .map(purchaseMapper::toDTO);
        return page;
    }

}
