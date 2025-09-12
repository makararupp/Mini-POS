package makara.co.min_pos.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import makara.co.min_pos.exception.ResourceNotFoundException;
import makara.co.min_pos.mapper.SaleMapper;
import makara.co.min_pos.models.entities.ItemProduct;
import makara.co.min_pos.models.entities.Sale;
import makara.co.min_pos.models.entities.SaleDetail;
import makara.co.min_pos.models.enums.PaymentStatus;
import makara.co.min_pos.models.response.SaleResponse;
import makara.co.min_pos.repository.ProductRepository;
import makara.co.min_pos.repository.SaleDetailRepository;
import makara.co.min_pos.repository.SaleRepository;
import makara.co.min_pos.service.SaleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleMapper itemSaleMapper;
    private final SaleDetailRepository detailRepository;
    private final ProductRepository productRepository;

    @Override
    public Sale create(Sale sale) {
        if (sale.getAmount() == null || sale.getAmount() <= 0) {
            throw new IllegalArgumentException("Sale amount must be greater than zero");
        }
        return saleRepository.save(sale);
    }

    @Override
    public Sale getById(Long id) {
        return saleRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale", id));
    }

    @Override
    public List<Sale> getSalesByStatusAndDate(PaymentStatus status, LocalDate date) {
        List<Sale> sales = saleRepository.findByPaymentStatusAndSoldDate(status, date);
        if (sales.isEmpty()) {
            return null;
        }
        return sales;
    }

    @Override
    public List<SaleResponse> getAllSold() {
        return saleRepository.findAllByIsDeletedFalse()
                .stream()
                .map(itemSaleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> getSalesByStatusAndDate(
            LocalDate startDate, LocalDate endDate, PaymentStatus status) {
        return saleRepository.findSalesByDateRangeAndStatus(startDate, endDate, status);
    }

    @Override
    public Sale updatePayment(Long id, PaymentStatus status) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale" + id));
        sale.setPaymentStatus(status);
        return saleRepository.save(sale);
    }

    @Override
    public Sale delete(Long id) {
        Sale sale = getById(id);
        sale.setIsDeleted(true);
        Sale save = saleRepository.save(sale);
       return save;
    }

    @Override
    @Transactional
    public Sale cancelSale(Long saleId) {
        // Step1 :update sale status
        Sale sale = getById(saleId);
        saleRepository.save(sale);

        List<SaleDetail> saleDetails =  detailRepository.findBySaleId(saleId);
        if (saleDetails.isEmpty()){
            throw new IllegalStateException("No SaleDetails found for Sale ID: " + saleId);
        }
        // Step 2: Validate the sale status
        if(sale.getPaymentStatus() == PaymentStatus.PAID){
            throw  new IllegalArgumentException("Cannot cancel a PAID sale");
        }
        // Step 3: Query SaleDetails dynamically using specifications
        List<Long> productIds = saleDetails.stream()
                .map(sd -> sd.getProduct().getId())
                .toList();

        List<ItemProduct> products = productRepository.findByIdInAndIsDeletedFalse((Set<Long>) productIds);

        if(products.isEmpty()){
            throw new IllegalArgumentException("No product found the given IDs");
        }

        Map<Long, ItemProduct> productMap = products.stream()
                .collect(Collectors.toMap(ItemProduct::getId, Function.identity()));

        // Step 4: Update Stock Quantities
        saleDetails.forEach(sd -> {
            ItemProduct product = productMap.get(sd.getProduct().getId());
            product.setQty(product.getQty() + sd.getUnit());
        });

        productRepository.saveAll(products);
        return sale;
    }

}
