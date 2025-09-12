package makara.co.min_pos.service.impl;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.models.DTO.report.ExpenseReportDTO;
import makara.co.min_pos.models.entities.ImportProductHistory;
import makara.co.min_pos.models.entities.ItemProduct;
import makara.co.min_pos.models.entities.Purchase;
import makara.co.min_pos.models.enums.PurchaseStatus;
import makara.co.min_pos.repository.ProductImportHistoriesRepository;
import makara.co.min_pos.repository.ProductRepository;
import makara.co.min_pos.repository.PurchaseRepository;
import makara.co.min_pos.service.ReportService;
import makara.co.min_pos.spec.ProductImportHistoryFilter;
import makara.co.min_pos.spec.ProductImportHistorySpec;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final PurchaseRepository purchaseRepository;
    private final ProductImportHistoriesRepository productImportHistoriesRepository;
    private final ProductRepository productRepository;


    @Override
    public List<Purchase> getPurchaseSold(PurchaseStatus status,
                                          LocalDate startDate, LocalDate endDate) {
        return purchaseRepository.findPurchasesByStatusAndDateRange(status,startDate,endDate);
    }

    @Override
    public List<ExpenseReportDTO> getExpenseReport(LocalDate startDate, LocalDate endDate) {
        ProductImportHistoryFilter  importHistoryFilter = new ProductImportHistoryFilter();
        importHistoryFilter.setStartDate(startDate);
        importHistoryFilter.setEndDate(endDate);

        ProductImportHistorySpec spec = new ProductImportHistorySpec(importHistoryFilter);
        List<ImportProductHistory> importHistories = productImportHistoriesRepository.findAll();

        Set<Long> productIds = importHistories.stream()
                .map(his -> his.getProduct().getId())
                .collect(Collectors.toSet());

        List<ItemProduct> products = productRepository.findByIdInAndIsDeletedFalse(productIds);
        Map<Long, ItemProduct> productMap = products.stream()
                .collect(Collectors.toMap(p -> p.getId(), p -> p));

        Map<ItemProduct, List<ImportProductHistory>> importMap = importHistories.stream()
                .collect(Collectors.groupingBy(pi -> pi.getProduct()));

        var expenseReportDTOs = new ArrayList<ExpenseReportDTO>();

        for(var entry : importMap.entrySet()){
            ItemProduct product = productMap.get(entry.getKey().getId());
            List<ImportProductHistory> importProducts= entry.getValue();

            int totalUnit = importProducts.stream()
                    .mapToInt(pi -> pi.getImportUnit())
                    .sum();
            double totalAmount = importProducts.stream()
                    .mapToDouble(pi -> pi.getImportUnit() * pi.getPricePerUnit().doubleValue())
                    .sum();

            var expenseReportDTO = new ExpenseReportDTO();
            expenseReportDTO.setProductId(product.getId());
            expenseReportDTO.setProductName(product.getProductName());
            expenseReportDTO.setTotalUnit(product.getQty());
            expenseReportDTO.setTotalAmount(BigDecimal.valueOf(totalAmount));
            expenseReportDTOs.add(expenseReportDTO);
        }
        Collections.sort(expenseReportDTOs,(a,b)->(int)(a.getProductId()- b.getProductId()));
        return expenseReportDTOs;
    }
}
